package com.iclean.playlistmaker.player.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.databinding.ActivityPlayerBinding
import com.iclean.playlistmaker.general.TrackMethods
import com.iclean.playlistmaker.main.ui.RootActivity
import com.iclean.playlistmaker.player.domain.OnCompletionListener
import com.iclean.playlistmaker.player.domain.OnPreparedListener
import com.iclean.playlistmaker.player.domain.api.ClickPlaylist
import com.iclean.playlistmaker.player.presentation.PlayerViewModel
import com.iclean.playlistmaker.search.domain.models.Track
import com.iclean.playlistmaker.search.ui.SearchFragment.Companion.DELAY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayerActivity : AppCompatActivity() {

    //Создаем ViewModel, Adapter и Binding
    private val viewModel by viewModel<PlayerViewModel>()
    private lateinit var binding : ActivityPlayerBinding
    private lateinit var adapter: PlaylistForPlayerAdapter

    //Задаем переменную трека и состояния избранного
    private lateinit var track : Track

    //Подключаем нужные обработчики к Activity
    private val trackMethods = TrackMethods()

    //Определяем переменные, которые пригодятся позже
    private var timeFormat : String? = ""
    private var isClickAllowed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Определяем "нулевое время"
        val nullTime = resources.getString(R.string.null_time)

        //Задаем BottomSheet
        val bottomSheetContainer = binding.bottomSheet
        val bottomSheetBehaivor = BottomSheetBehavior.from(bottomSheetContainer)
        bottomSheetBehaivor.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetBehaivor.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> binding.overlay.visibility = View.GONE
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.overlay.visibility = View.VISIBLE
                        viewModel.returnPlaylists()
                    }
                    else -> binding.overlay.visibility = View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        //Добавляем трек в плейлист по клику на трек
        val playlistClick = object : ClickPlaylist {
            override fun addTrackInPlaylist(playlist: Playlist) {
                if(clickDebounce()) {
                    if(playlist.playlistList?.contains(track.trackId)!!) {
                        Toast.makeText(
                            this@PlayerActivity,
                            "Трек уже добавлен в плейлист ${playlist.playlistName}",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        viewModel.updatePlaylist(
                            Playlist(
                                playlist.id,
                                playlist.playlistName,
                                playlist.playlistDescription,
                                playlist.plailistImage,
                                playlist.playlistList + track.trackId + ", ",
                                playlist.playlistCount + 1)
                        )
                        viewModel.insertTrackInPlaylist(track)
                        bottomSheetBehaivor.state = BottomSheetBehavior.STATE_HIDDEN
                        Toast.makeText(
                            this@PlayerActivity,
                            "Добавлено в плейлист ${playlist.playlistName}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        //Получаем плейлисты
        adapter = PlaylistForPlayerAdapter(playlistClick)
        adapter.submitList(listOf())

        viewModel.getLiveDataPlaylist().observe(this as LifecycleOwner) {
            if(it.status != 1) {
                adapter.submitList(it.playlists)
            } else {
                adapter.submitList(listOf())
            }
            binding.playlists.adapter = adapter
            binding.playlists.layoutManager = LinearLayoutManager(this)
        }

        //Возвращаемся домой
        binding.backButton.setOnClickListener {
            finish()
        }


        //Работаем с наблюдателями - УСТАНАВЛИВАЕМ ДАННЫЕ В ПЛЕЕР
        viewModel.getTrack(intent).observe(this) {
            //Достаем основные переменные - подготавливаем время в нужном формате
            val timeNoFormat = it.time.toString()
            timeFormat = trackMethods.dateFormatTrack(timeNoFormat)

            track = it.track
            //Смотрим, есть ли в базе данных трек
            lifecycleScope.launch(Dispatchers.IO) {
                val isFavorite = viewModel.checkFavoriteState(track.trackId.toInt())
                track = track.copy(isFavorite = isFavorite)
                setImages(isFavorite)
            }

            //Устанавливаем поля для трека
            setDataForView(track)
            //Устанавливаем обложку
            setPoster(track.artworkUrl100)
            //Выводим альбом, только если информация передана
            showCollection(track.collectionName)
            //Определяем добавлерн ли трек в избранное


        }

        //Прописываем методы подготовки плеера
        viewModel.preparePlayer()

        viewModel.setOnPreparedListener(object: OnPreparedListener {
            override fun onPrepared() {
                binding.buttonPlay.isEnabled = true
            }
        })

        viewModel.setOnCompletionListener(object : OnCompletionListener {
            override fun onCompletion() {
                viewModel.setPreparedState()
                setImagePlay()
                stopTimer()
                binding.timer.text = nullTime
            }
        })



        //Управляем нажатиями кнопок
        binding.buttonPlay.setOnClickListener {
            buttonCheck()
        }

        binding.buttonHeart.setOnClickListener {
            viewModel.onFavoriteClicked(track)
        }

        binding.buttonPlus.setOnClickListener {
            bottomSheetBehaivor.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.newButton.setOnClickListener {
            val intent = Intent(this, RootActivity::class.java)
            intent.putExtra("destination", "to_create_playlist")
            startActivity(intent)
            bottomSheetBehaivor.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }
    //КОНЕЦ МЕТОДА onCreate


    //МЕТОДЫ ФУНКЦИОНАЛА УРОВНЯ UI
    //Присваиваем нужные значения блокам экрана
    private fun setDataForView(track : Track) {
        binding.titleTrack.text = track.trackName
        binding.artistName.text = track.artistName
        binding.year.text = track.releaseDate.substring(0, 4)
        binding.genre.text = track.primaryGenreName
        binding.country.text = track.country
        binding.timer.text = timeFormat
        val timeMillis = track.trackTimeMillis
        binding.time.text = trackMethods.dateFormatTrack(timeMillis)
    }

    //Управляем видимостью названия альбома
    private fun showCollection(collection : String) {
        if (collection.isEmpty()) {
            binding.album.isVisible = false
            binding.albumTrack.isVisible = false
        } else {
            binding.album.text = collection
        }
    }

    //Устанавливаем обложку
    private fun setPoster(url : String){
        trackMethods.setImage(
            applicationContext,
            url.replaceAfterLast('/', "512x512bb.jpg"),
            binding.poster,
            R.drawable.album,
            8.0f
        )
    }


    //Переключаем кнопку
    private fun buttonCheck() {
        if(viewModel.buttonCheck()) setImagePlay()
        else setImagePause()
    }

    private fun setImagePause() {
        binding.buttonPlay.setImageResource(R.drawable.pause)
    }

    private fun setImagePlay() {
        binding.buttonPlay.setImageResource(R.drawable.play)
    }


    private fun stopTimer() {
        viewModel.stopTimer()
    }

    private fun setImages(isFavorite : Boolean) {
        //Проверяем, если ли трек в избранном, и выводим соответствующую кнопку
        if(isFavorite) {
            binding.buttonHeart.setImageResource(R.drawable.button_red)

        } else {
            binding.buttonHeart.setImageResource(R.drawable.heart)

        }

    }

    //Задержка действий по клику
    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            lifecycleScope.launch {
                delay(DELAY)
                isClickAllowed = true
            }

        }
        return current
    }



    //Переопределяем системные методы
    override fun onDestroy() {
        super.onDestroy()
        viewModel.release()
        stopTimer()
    }

    override fun onPause() {
        super.onPause()
        if(viewModel.isPlaying()) {
            viewModel.pausePlayer()
            setImagePlay()
            stopTimer()
        }
    }

}
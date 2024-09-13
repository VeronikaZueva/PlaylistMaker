package com.iclean.playlistmaker.player.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.databinding.ActivityPlayerBinding
import com.iclean.playlistmaker.general.TrackMethods
import com.iclean.playlistmaker.player.domain.OnCompletionListener
import com.iclean.playlistmaker.player.domain.OnPreparedListener
import com.iclean.playlistmaker.player.presentation.PlayerViewModel
import com.iclean.playlistmaker.search.domain.models.Track
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayerActivity : AppCompatActivity() {

    //Создаем ViewModel и Binding
    private val viewModel by viewModel<PlayerViewModel>()
    private lateinit var binding : ActivityPlayerBinding

    //Подключаем нужные обработчики к Activity
    private val trackMethods = TrackMethods() //Общие методы

    //Определяем переменные, которые пригодятся позже
    private var timeFormat : String? = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Определяем "нулевое время"
        val nullTime = resources.getString(R.string.null_time)

        //Возвращаемся домой
        binding.backButton.setOnClickListener {
            this.finish()
        }

        //УСТАНАВЛИВАЕМ ДАННЫЕ В ПЛЕЕР
        viewModel.getTrack(intent).observe(this) {
            //Достаем основные переменные - подготавливаем время в нужном формате
            val timeNoFormat = it.time.toString()
            timeFormat = trackMethods.dateFormatTrack(timeNoFormat)
            //Устанавливаем поля для трека
            val track = it.track
            setDataForView(track)
            //Устанавливаем обложку
            setPoster(track.artworkUrl100)
            //Выводим альбом, только если информация передана
            showCollection(track.collectionName)
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
                setImagePlay()
                viewModel.removeCallback()
                binding.timer.text = nullTime
            }
        })



        //Управляем нажатиями кнопок: play|pause
        binding.buttonPlay.setOnClickListener {
            buttonCheck()
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

    //Переопределяем системные методы
    override fun onDestroy() {
        super.onDestroy()
        viewModel.release()
        viewModel.removeCallbacksAndMessages()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
        setImagePlay()
        viewModel.removeCallback()
    }


}
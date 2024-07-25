package com.iclean.playlistmaker.player.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.iclean.playlistmaker.R
import androidx.lifecycle.ViewModelProvider
import com.iclean.playlistmaker.databinding.ActivityPlayerBinding
import com.iclean.playlistmaker.general.TrackMethods
import com.iclean.playlistmaker.player.presentation.PlayerViewModel
import com.iclean.playlistmaker.search.domain.models.Track


class PlayerActivity : AppCompatActivity() {

    //Создаем ViewModel и Binding
    private lateinit var viewModel : PlayerViewModel
    private lateinit var binding : ActivityPlayerBinding

    //Подключаем нужные обработчики к Activity
    private val trackMethods = TrackMethods() //Общие методы

    //Данные, которые будут перезаписаны позже
    private lateinit var previewUrl : String
    private lateinit var collectionName : String

    companion object {
        const val nullTime = "00:00"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            PlayerViewModel.getViewModelFactory()
        )[PlayerViewModel::class.java]

        //Возвращаемся домой
        binding.backButton.setOnClickListener {
            this.finish()
        }

        //Прописываем метода подготовки плеера
        viewModel.pausePlayer()

        viewModel.setOnPreparedListener {
            binding.buttonPlay.isEnabled = true
        }

        viewModel.setOnCompletionListener {
            setImagePlay()
            viewModel.removeCallback()
            binding.timer.text = nullTime
        }

        //ОСНОВНОЙ ОБРАБОТЧИК
        viewModel.getTrack(intent).observe(this) {
            //Достаем основные переменные - это сам трэк и время для обработки
            val time = it.time.toString()
            val track = it.track
            trackMethods.dateFormatTrack(time)
            setDataForView(track)
            setPoster(previewUrl)
            showCollection(collectionName)
        }


        //Управляем нажатиями кнопок: play|pause
        binding.buttonPlay.setOnClickListener {
            buttonCheck()
        }
    }
    //КОНЕЦ МЕТОДА onCreate
    //Сразу определяем переменные binding
    private val titleTrack = binding.titleTrack
    private val artistTrackName = binding.artistName
    private val timeTrack = binding.time
    private val albumTrack = binding.albumTrack
    private val album = binding.album
    private val year = binding.year
    private val genre = binding.genre
    private val countryTrack = binding.country
    private val poster = binding.poster
    private val placeholder = R.drawable.album
    private val playButton = binding.buttonPlay
    private lateinit var url : String

    //Присваиваем нужные значения блокам экрана
    private fun setDataForView(track : Track) {
        titleTrack.text = track.trackName
        artistTrackName.text = track.artistName
        timeTrack.text = trackMethods.dateFormatTrack(track.trackTimeMillis)
        url = track.artworkUrl100!!
        year.text = track.releaseDate!!.substring(0, 4)
        genre.text = track.primaryGenreName
        countryTrack.text = track.country
        previewUrl = track.previewUrl!!
    }

    //Управляем видимостью названия альбома
    private fun showCollection(collection : String) {
        if (collection.isEmpty()) {
            album.isVisible = false
            albumTrack.isVisible = false
        } else {
            album.text = collection
        }
    }

    //Устанавливаем обложку
    private fun setPoster(url : String){
        trackMethods.setImage(
            applicationContext,
            url.replaceAfterLast('/', "512x512bb.jpg"),
            poster,
            placeholder,
            8.0f
        )
    }


    //Переключаем кнопку
    private fun buttonCheck() {
        if(viewModel.buttonCheck()) setImagePause()
        else setImagePlay()
    }

    private fun setImagePause() {
        playButton.setImageResource(R.drawable.pause)
    }

    private fun setImagePlay() {
        playButton.setImageResource(R.drawable.play)
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
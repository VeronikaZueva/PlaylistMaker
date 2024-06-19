package com.iclean.playlistmaker.player.presentation.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.player.presentation.presents.PlayerActivityPresents
import androidx.core.view.isVisible
import com.iclean.playlistmaker.TrackResponse
import com.iclean.playlistmaker.data.repository.PlayerRepositoryImpl
import com.iclean.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.iclean.playlistmaker.player.domain.models.MediaPlayerState


@Suppress("DEPRECATION")
class PlayerActivity : AppCompatActivity() {

    //Подключаем нужные обработчики к Activity
    private val presents = PlayerActivityPresents() //Получает данные
    private val player = PlayerRepositoryImpl() //Обработчик переключателя
    private val interactor = PlayerInteractorImpl(player) //Основное взаимодействие
    private val trackMethods = TrackMethods() //Общие методы


    //Определяем переменные, которые нам потребуются позже
    private var mainThread: Handler? = null
    lateinit var timerTrack : TextView
    private lateinit var playButton : ImageButton
    private lateinit var url : String
    private var isEnable : Boolean = false
    private lateinit var state : MediaPlayerState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        mainThread = Handler(Looper.getMainLooper())

        //Возвращаемся домой
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            this.finish()
        }


        //Определяем переменные полей
        val titleTrack = findViewById<TextView>(R.id.title_track)
        val artistTrackName = findViewById<TextView>(R.id.artist_name)
        val timeTrack = findViewById<TextView>(R.id.time)
        timerTrack = findViewById(R.id.timer)
        val albumTrack = findViewById<TextView>(R.id.album_track)
        val album = findViewById<TextView>(R.id.album)
        val year = findViewById<TextView>(R.id.year)
        val genre = findViewById<TextView>(R.id.genre)
        val countryTrack = findViewById<TextView>(R.id.country)
        val poster = findViewById<ImageView>(R.id.poster)
        val placeholder = R.drawable.album
        playButton = findViewById(R.id.button_play)



        //Принимаем данные из Интента
        val data = intent.extras
        val trackItem = data?.getParcelable<TrackResponse.Track>(/* key = */ "trackObject")

        //Присваиваем нужные значения блокам экрана
        titleTrack.text = presents.getTrackName(trackItem)
        artistTrackName.text = presents.getArtistName(trackItem)
        timerTrack.text = trackMethods.dateFormatTrack(presents.getTrackTimeMillis(trackItem))
        timeTrack.text = trackMethods.dateFormatTrack(presents.getTrackTimeMillis(trackItem))
        url = presents.getArtworkUrl100(trackItem)
        album.text = presents.getCollectionName(trackItem)
        year.text = presents.getYearFormat(trackItem)
        genre.text = presents.getPrimaryGenreName(trackItem)
        countryTrack.text = presents.getCountry(trackItem)

        if(presents.getCollectionName(trackItem).isEmpty()) {
            album.isVisible = false
            albumTrack.isVisible = false
        }

        //Устанавливаем обложку
        trackMethods.setImage(applicationContext, presents.getBigPoster(trackItem), poster, placeholder, 8.0f)

        //Работаем с медиаплеером
        state = interactor.preparePlayer(url)

        //Обработчик нажатия на кнопку play|pause
        playButton.setOnClickListener {
            isEnable = interactor.playControl(state)
            changeButton(isEnable)
             createTimerControl()
        }


    }
    //End OnCreate

    //Управляем таймером
   private fun createTimerControl() {
        mainThread?.post(createTimerTask())
    }

   private fun createTimerTask() : Runnable {
        return object : Runnable {
            override fun run() {
                when (state) {
                    MediaPlayerState.STATE_PAUSED -> mainThread?.removeCallbacks(this)
                    else -> {
                        timerTrack.text = interactor.statusTimer(state).toString()

                        mainThread?.postDelayed(this, PlayerActivityPresents.DELAY)
                    }
                }

            }
        }
    }


    //Ставим плеер на паузу, если пользователь свернул экран
    override fun onPause() {
        super.onPause()
        player.pausePlayer()
        changeButton(false)
    }
    //Управляем воспроизведением, переопределяя onDestroy()
    override fun onDestroy() {
        super.onDestroy()
        player.release()
        mainThread?.removeCallbacksAndMessages(null)
    }



    private fun changeButton(isEnableButton : Boolean) {
       if(isEnableButton) {
            playButton.setImageResource(R.drawable.pause)
            state = interactor.startPlayer(isEnableButton)
            isEnable = false
        } else {
            playButton.setImageResource(R.drawable.play)
            state = interactor.pausePlayer(isEnableButton)
            isEnable = true
        }
    }




}
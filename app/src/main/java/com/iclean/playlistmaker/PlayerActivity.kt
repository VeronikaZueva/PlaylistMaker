package com.iclean.playlistmaker


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

@Suppress("DEPRECATION")
class PlayerActivity : AppCompatActivity() {

    companion object {
        private const val DELAY = 300L
    }

    private val  trackMethods = TrackMethods()
    private val trackPlayer = TrackMediaPlayer()
    private var mainThread : Handler? = null
    private lateinit var timerTrack : TextView

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        mainThread = Handler(Looper.getMainLooper())

        //Возвращаемся домой
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            this.finish()
        }

        //Принимаем данные из Интента
        val data = intent.extras
        val trackItem = data?.getParcelable<TrackResponse.Track>(/* key = */ "trackObject")
        val trackName = trackItem?.trackName.toString()
        val artistName = trackItem?.artistName.toString()
        val trackTimeMillis = trackItem?.trackTimeMillis
        val artworkUrl100 = trackItem?.artworkUrl100.toString()
        val collectionName = trackItem?.collectionName.toString()
        val releaseData = trackItem?.releaseDate.toString()
        val primaryGenreName = trackItem?.primaryGenreName.toString()
        val country = trackItem?.country.toString()
        trackPlayer.url = trackItem?.previewUrl.toString()


        //Преобразуем ссылку на изображение в полный формат
        val bigPoster = artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
        val yearFormat = releaseData.substring(0, 4)

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
        trackPlayer.playButton = findViewById(R.id.button_play)
        val url = trackPlayer.url

        //Присваиваем нужные значения блокам экрана
        titleTrack.text = trackName
        artistTrackName.text = artistName
        timeTrack.text = trackMethods.dateFormatTrack(trackTimeMillis)
        timerTrack.text = trackMethods.dateFormatTrack(trackTimeMillis)
        album.text = collectionName
        year.text = yearFormat
        genre.text = primaryGenreName
        countryTrack.text = country

            if(collectionName.isEmpty()) {
            album.isVisible = false
            albumTrack.isVisible = false
        }

        //Устанавливаем обложку
        trackMethods.setImage(applicationContext, bigPoster, poster, placeholder, 8.0f)

        //Работаем с медиаплеером
        trackPlayer.preparePlayer(url)
        trackPlayer.playButton.setOnClickListener {
            trackPlayer.playControl()
            createTimerControl(trackPlayer.playerState)
        }


    }
    //Управляем таймером
    private fun createTimerControl(playerState : Int) {
        mainThread?.post(createTimerTask(playerState))
    }
    private fun createTimerTask(playerState : Int) : Runnable {
           return object : Runnable {
                override fun run() {
                    when(playerState) {
                        3 -> mainThread?.removeCallbacks(this)
                        else -> {
                            timerTrack.text = trackPlayer.statusTimer((playerState))
                            mainThread?.postDelayed(this, DELAY)
                        }
                    }

                }
            }

    }

    //Ставим плеер на паузу, если пользователь свернул экран
    override fun onPause() {
        super.onPause()
        trackPlayer.pausePlayer()
    }
    //Управляем воспроизведением, переопределяя onDestroy()
    override fun onDestroy() {
        super.onDestroy()
        trackPlayer.mediaPlayer.release()
        mainThread?.removeCallbacksAndMessages(null)
    }
}
package com.iclean.playlistmaker


import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

@Suppress("DEPRECATION")
class PlayerActivity : AppCompatActivity() {
    private val  trackMethods = TrackMethods()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

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


        //Преобразуем ссылку на изображение в полный формат
        val bigPoster = artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
        val yearFormat = releaseData.substring(0, 4)

        //Определяем переменные полей
        val titleTrack = findViewById<TextView>(R.id.title_track)
        val artistTrackName = findViewById<TextView>(R.id.artist_name)
        val timeTrack = findViewById<TextView>(R.id.time)
        val timerTrack = findViewById<TextView>(R.id.timer)
        val albumTrack = findViewById<TextView>(R.id.album_track)
        val album = findViewById<TextView>(R.id.album)
        val year = findViewById<TextView>(R.id.year)
        val genre = findViewById<TextView>(R.id.genre)
        val countryTrack = findViewById<TextView>(R.id.country)
        val poster = findViewById<ImageView>(R.id.poster)
        val placeholder = R.drawable.album

        //Присваиваем нужнеы значения блокам экрана
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

        trackMethods.setImage(applicationContext, bigPoster, poster, placeholder)


    }
}
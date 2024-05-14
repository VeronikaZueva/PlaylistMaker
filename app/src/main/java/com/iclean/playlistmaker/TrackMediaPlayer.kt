package com.iclean.playlistmaker

import android.media.MediaPlayer
import android.widget.ImageButton

class TrackMediaPlayer {
    companion object {
        //Константы состояний медиаплеера
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val START_TIME = "00:00"
    }

    //Начальные переменные класса
    lateinit var playButton : ImageButton
    lateinit var url : String
    var playerState = STATE_DEFAULT

    //Создаем экземпляры классов, с которыми будем работать
    val mediaPlayer = MediaPlayer()
    private val trackMethods = TrackMethods()


    //Функция состояний подготовки плеера
     fun preparePlayer(url : String) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playButton.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playButton.setImageResource(R.drawable.play)
            playerState = STATE_PREPARED
        }
    }

    //Функции состояния воспроизведения
    private fun startPlayer() {
        mediaPlayer.start()
        playButton.setImageResource(R.drawable.pause)
        playerState = STATE_PLAYING
    }
    fun pausePlayer() {
        mediaPlayer.pause()
        playButton.setImageResource(R.drawable.play)
        playerState = STATE_PAUSED
    }

    //Функция переключения воспроизведения
    fun playControl() {
        when(playerState) {
            STATE_PLAYING -> {pausePlayer()}
            STATE_PREPARED, STATE_PAUSED -> {startPlayer()}
        }
    }

    //Обработка таймера
    fun statusTimer(timerState : Int) : String? {
        return when (timerState) {
            0 -> START_TIME
            1 -> START_TIME
            2 -> {
                val current = mediaPlayer.currentPosition.toString()
                trackMethods.dateFormatTrack(current)
            }
            3 -> {
                val current = mediaPlayer.currentPosition.toString()
                trackMethods.dateFormatTrack(current)
            }
            else -> START_TIME
        }

    }}
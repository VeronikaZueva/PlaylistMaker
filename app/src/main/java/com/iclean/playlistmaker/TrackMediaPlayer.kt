package com.iclean.playlistmaker

import android.media.MediaPlayer
import android.widget.ImageButton

class TrackMediaPlayer : TrackMediaPlayerInterface {
    private companion object {
        //Константы состояний медиаплеера
        const val STATE_DEFAULT = 0
        const val STATE_PREPARED = 1
        const val STATE_PLAYING = 2
        const val STATE_PAUSED = 3
        const val START_TIME = "00:00"
    }

    //Начальные переменные класса
    override lateinit var playButton : ImageButton
    override lateinit var url : String
    override var playerState = STATE_DEFAULT

    //Создаем экземпляры классов, с которыми будем работать
    override val mediaPlayer = MediaPlayer()
    override val trackMethods = TrackMethods()


    //Функция состояний подготовки плеера
     override fun preparePlayer(url : String) {
        with(mediaPlayer) {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener {
                playButton.isEnabled = true
                playerState = STATE_PREPARED
            }
            setOnCompletionListener {
                playButton.setImageResource(R.drawable.play)
                playerState = STATE_PREPARED
            }
        }

    }

    //Функции состояния воспроизведения
    override fun startPlayer() {
        mediaPlayer.start()
        playButton.setImageResource(R.drawable.pause)
        playerState = STATE_PLAYING
    }
    override fun pausePlayer() {
        mediaPlayer.pause()
        playButton.setImageResource(R.drawable.play)
        playerState = STATE_PAUSED
    }

    //Функция переключения воспроизведения
    override fun playControl() {
        when(playerState) {
            STATE_PLAYING -> {pausePlayer()}
            STATE_PREPARED, STATE_PAUSED -> {startPlayer()}
        }
    }

    //Обработка таймера
    override fun statusTimer(timerState : Int) : String? {
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
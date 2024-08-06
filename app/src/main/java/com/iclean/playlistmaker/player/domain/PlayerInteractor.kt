package com.iclean.playlistmaker.player.domain

import android.media.MediaPlayer

interface PlayerInteractor {
    //Медиаплеер
    fun preparePlayer()

    fun setOnPreparedListener(listener : MediaPlayer.OnPreparedListener)
    fun setOnCompletionListener(listener : MediaPlayer.OnCompletionListener)

    fun startPlayer()
    fun pausePlayer()

    fun release()
    fun getCurrentPosition() : Int
    //Handler
    fun postTimerDelay(delay : Long)
    fun removeCallback()
    fun removeCallbacksAndMessages()

}
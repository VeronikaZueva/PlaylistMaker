package com.iclean.playlistmaker.player.domain

interface PlayerInteractor {
    //Медиаплеер
    fun preparePlayer()

    fun setOnPreparedListener()
    fun setOnCompletionListener()

    fun startPlayer()
    fun pausePlayer()

    fun release()
    fun getCurrentPosition() : Int
    //Handler
    fun postTimerDelay(delay : Long)
    fun removeCallback()
    fun removeCallbacksAndMessages()

}
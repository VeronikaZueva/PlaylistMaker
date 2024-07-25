package com.iclean.playlistmaker.player.domain

import android.media.MediaPlayer

interface PlayerInteractor {

    fun preparePlayer()

    fun setOnPreparedListener(listener : MediaPlayer.OnPreparedListener)
    fun setOnCompletionListener(listener : MediaPlayer.OnCompletionListener)

    fun startPlayer()
    fun pausePlayer()

    fun release()
    fun getCurrentPosition() : Int


}
package com.iclean.playlistmaker.player.data
import android.media.MediaPlayer

interface PlayerRepository {

    fun preparePlayer()

    fun setOnPreparedListener(listener : MediaPlayer.OnPreparedListener)
    fun setOnCompletionListener(listener : MediaPlayer.OnCompletionListener)

    fun startPlayer()
    fun pausePlayer()

    fun release()
    fun getCurrentPosition() : Int


}
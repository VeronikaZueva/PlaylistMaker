package com.iclean.playlistmaker.player.domain

interface PlayerRepository {

    fun preparePlayer(previewUrl : String)

    fun setOnPreparedListener(listener : OnPreparedListener)
    fun setOnCompletionListener(listener: OnCompletionListener)

    fun startPlayer()
    fun pausePlayer()

    fun release()
    fun getCurrentPosition() : Int

    fun isPlaying() : Boolean

}
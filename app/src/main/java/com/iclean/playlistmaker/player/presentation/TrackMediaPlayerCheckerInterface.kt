@file:Suppress("UNREACHABLE_CODE")

package com.iclean.playlistmaker.player.presentation
import com.iclean.playlistmaker.player.domain.models.MediaPlayerState

interface TrackMediaPlayerCheckerInterface {

    fun preparePlayer(url : String) : MediaPlayerState {
        return TODO("Provide the return value")
    }

    fun startPlayer()  {}

    fun pausePlayer()  {}

    fun playControl() : Boolean  {
        return TODO("Provide the return value")
    }

    fun statusTimer(): String? {
        return TODO("Provide the return value")
    }

    fun release()

    fun getState() : MediaPlayerState {
        return TODO("Provide the return value")
    }

}
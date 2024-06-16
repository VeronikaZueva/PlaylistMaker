@file:Suppress("UNREACHABLE_CODE")

package com.iclean.playlistmaker.player.domain.repository

import com.iclean.playlistmaker.player.domain.models.MediaPlayerState

interface TrackMediaPlayerInterface {
    fun defaultPlayerState() : MediaPlayerState {
        return TODO("Provide the return value")
    }
    fun preparePlayer(url : String): MediaPlayerState  {
        return TODO("Provide the return value")
    }

    fun startPlayer() : MediaPlayerState  {
        return TODO("Provide the return value")
    }

    fun pausePlayer() : MediaPlayerState {
        return TODO("Provide the return value")
    }
        fun statusTimer(): String? {
        return TODO("Provide the return value")
    }


}
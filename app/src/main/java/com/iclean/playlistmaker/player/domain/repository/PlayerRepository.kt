@file:Suppress("UNREACHABLE_CODE")

package com.iclean.playlistmaker.player.domain.repository
import com.iclean.playlistmaker.player.domain.models.MediaPlayerState

interface PlayerRepository {

    fun preparePlayer(url : String) : MediaPlayerState {
        return TODO("Provide the return value")
    }

    fun startPlayer() : MediaPlayerState {
        return TODO("Provide the return value")
    }

    fun pausePlayer() : MediaPlayerState {
        return TODO("Provide the return value")
    }


    fun statusTimer(statePlayer : MediaPlayerState): String? {
        return TODO("Provide the return value")
    }

    fun release()

    fun getDefaultState() : MediaPlayerState {
        return TODO("Provide the return value")
    }

    fun getState(isEnable : Boolean) : MediaPlayerState {
        return TODO("Provide the return value")
    }

}
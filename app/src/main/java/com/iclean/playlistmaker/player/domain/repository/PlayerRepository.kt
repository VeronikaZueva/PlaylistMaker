@file:Suppress("UNREACHABLE_CODE")

package com.iclean.playlistmaker.player.domain.repository
import com.iclean.playlistmaker.data.models.MediaPlayerState

interface PlayerRepository {

    var state : MediaPlayerState

    fun preparePlayer(previewUrl : String)  {
        return TODO("Provide the return value")
    }

    fun startPlayer()  {
        return TODO("Provide the return value")
    }

    fun pausePlayer()  {
        return TODO("Provide the return value")
    }


    fun statusTimer(statePlayer : MediaPlayerState): String? {
        return TODO("Provide the return value")
    }

    fun release()


}
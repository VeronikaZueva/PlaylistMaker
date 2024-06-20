package com.iclean.playlistmaker.player.domain.interactor

import com.iclean.playlistmaker.data.models.MediaPlayerState


interface PlayerInteractor {

    fun getPlayerState() : MediaPlayerState
    fun preparePlayer(previewUrl : String)

    fun startPlayer()
    fun pausePlayer()

    fun release()

    fun statusTimer(statePlayer : MediaPlayerState) : String?
}
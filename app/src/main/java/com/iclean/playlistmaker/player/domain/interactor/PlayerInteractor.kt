package com.iclean.playlistmaker.player.domain.interactor

import com.iclean.playlistmaker.player.domain.models.MediaPlayerState


interface PlayerInteractor {

    fun preparePlayer(url : String) : MediaPlayerState

    fun playControl(statePlayer : MediaPlayerState) : Boolean
    fun startPlayer(isEnable : Boolean) : MediaPlayerState
    fun pausePlayer(isEnable : Boolean) : MediaPlayerState

    fun statusTimer(statePlayer : MediaPlayerState) : String?
}
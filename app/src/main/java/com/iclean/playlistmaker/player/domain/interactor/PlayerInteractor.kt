package com.iclean.playlistmaker.player.domain.interactor

import com.iclean.playlistmaker.data.models.MediaPlayerState
import com.iclean.playlistmaker.player.domain.OnMediaPlayerStateChangeListener


interface PlayerInteractor {

    fun getPlayerState() : MediaPlayerState
    fun preparePlayer(previewUrl : String)

    fun startPlayer()
    fun pausePlayer()

    fun release()

    fun setOnChangeStateListener(onMediaPlayerStateChangeListener: OnMediaPlayerStateChangeListener)

    fun statusTimer(statePlayer : MediaPlayerState) : String?
}
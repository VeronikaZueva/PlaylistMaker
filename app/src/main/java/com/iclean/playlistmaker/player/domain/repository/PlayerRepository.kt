package com.iclean.playlistmaker.player.domain.repository
import com.iclean.playlistmaker.player.domain.models.MediaPlayerState
import com.iclean.playlistmaker.player.domain.OnMediaPlayerStateChangeListener

interface PlayerRepository {

    var state : MediaPlayerState

    fun preparePlayer(previewUrl: String?)

    fun startPlayer()
    fun pausePlayer()

    fun statusTimer(statePlayer : MediaPlayerState): String?

    fun release()

    fun setOnStateChangeListener(onMediaPlayerStateChangeListener : OnMediaPlayerStateChangeListener)
}
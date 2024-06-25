package com.iclean.playlistmaker.player.domain

import com.iclean.playlistmaker.player.domain.models.MediaPlayerState

interface OnMediaPlayerStateChangeListener {
    fun onChange(state : MediaPlayerState)
}
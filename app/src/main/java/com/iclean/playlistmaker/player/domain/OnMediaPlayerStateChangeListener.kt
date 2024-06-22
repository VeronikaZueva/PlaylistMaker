package com.iclean.playlistmaker.player.domain

import com.iclean.playlistmaker.data.models.MediaPlayerState

interface OnMediaPlayerStateChangeListener {
    fun onChange(state : MediaPlayerState)
}
package com.iclean.playlistmaker.player.domain.api

import com.iclean.playlistmaker.create.domain.models.Playlist

interface ClickPlaylist {
    fun addTrackInPlaylist(playlist: Playlist)
}
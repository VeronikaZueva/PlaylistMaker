package com.iclean.playlistmaker.media.domain.api

import com.iclean.playlistmaker.create.domain.models.Playlist

interface ClickPlaylistItem {
    fun goToPlaylist(playlist: Playlist)
}
package com.iclean.playlistmaker.playlist.presentation

import com.iclean.playlistmaker.create.domain.models.Playlist

sealed interface PlaylistItemState {
    data class PlaylistItemLiveData(val playlist : Playlist) : PlaylistItemState
    data object EmptyState : PlaylistItemState
}
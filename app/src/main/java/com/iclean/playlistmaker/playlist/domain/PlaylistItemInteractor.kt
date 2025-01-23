package com.iclean.playlistmaker.playlist.domain

import com.iclean.playlistmaker.create.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistItemInteractor {
    fun getPlaylistFromId(id : Int) : Flow<Playlist>
}
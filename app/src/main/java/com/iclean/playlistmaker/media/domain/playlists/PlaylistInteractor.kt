package com.iclean.playlistmaker.media.domain.playlists

import com.iclean.playlistmaker.create.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistInteractor {
    fun getPlaylists() : Flow<List<Playlist>>
}
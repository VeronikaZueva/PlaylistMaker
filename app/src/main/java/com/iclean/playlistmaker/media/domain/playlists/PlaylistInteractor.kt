package com.iclean.playlistmaker.media.domain.playlists

import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistInteractor {
    fun getPlaylists() : Flow<List<Playlist>>
    suspend fun updatePlaylist(playlist: Playlist)
    suspend fun insertTrackInPlaylist(track : Track)
}
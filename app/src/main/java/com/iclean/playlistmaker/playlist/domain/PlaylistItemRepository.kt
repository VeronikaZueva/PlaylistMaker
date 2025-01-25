package com.iclean.playlistmaker.playlist.domain

import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistItemRepository {
    fun getPlaylistFromId(id : Int) : Flow<Playlist>
    fun getTracksForPlaylist(trackIdList: List<Int>?) : Flow<List<Track>>
}
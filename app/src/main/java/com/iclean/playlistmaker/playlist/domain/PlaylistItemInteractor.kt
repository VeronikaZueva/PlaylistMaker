package com.iclean.playlistmaker.playlist.domain

import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistItemInteractor {
    fun getPlaylistFromId(id : Int) : Flow<Playlist>
    fun getTracksForPlaylist(tracksList: List<Int>?) : Flow<List<Track>>
    suspend fun checkTrackAllPlaylists(track : Int)
    fun sharePlaylist(playlistName : String, playlistDescription : String?, playlistCount : Int, trackList : List<Track>)
    suspend fun deletePlaylist(playlistId : Int)
}
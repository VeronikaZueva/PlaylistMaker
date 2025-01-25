package com.iclean.playlistmaker.playlist.domain.impl

import com.google.gson.reflect.TypeToken
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.playlist.domain.PlaylistItemInteractor
import com.iclean.playlistmaker.playlist.domain.PlaylistItemRepository
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class PlaylistItemInteractorImpl(private val playlistItemRepository: PlaylistItemRepository) : PlaylistItemInteractor {
    override fun getPlaylistFromId(id: Int): Flow<Playlist> {
        return playlistItemRepository.getPlaylistFromId(id)
    }

    override fun getTracksForPlaylist(tracksList: List<Int>?): Flow<List<Track>> {
        return playlistItemRepository.getTracksForPlaylist(tracksList)
    }


}
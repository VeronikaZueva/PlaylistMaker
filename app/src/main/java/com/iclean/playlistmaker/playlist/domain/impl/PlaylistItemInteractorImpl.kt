package com.iclean.playlistmaker.playlist.domain.impl


import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.playlist.domain.PlaylistItemInteractor
import com.iclean.playlistmaker.playlist.domain.PlaylistItemRepository
import com.iclean.playlistmaker.search.domain.models.Track
import com.iclean.playlistmaker.sharing.domain.SharingRepository
import kotlinx.coroutines.flow.Flow

class PlaylistItemInteractorImpl(private val playlistItemRepository: PlaylistItemRepository,
    private val shareRepository : SharingRepository) : PlaylistItemInteractor {
    override fun getPlaylistFromId(id: Int): Flow<Playlist> {
        return playlistItemRepository.getPlaylistFromId(id)
    }

    override fun getTracksForPlaylist(tracksList: List<Int>?): Flow<List<Track>> {
        return playlistItemRepository.getTracksForPlaylist(tracksList)
    }

    override suspend fun checkTrackAllPlaylists(track: Int) {
        playlistItemRepository.checkTrackAllPlaylist(track)
    }

    override fun sharePlaylist(playlistName : String, playlistDescription : String?, playlistCount : Int, trackList : List<Track>) {
        shareRepository.sharePlaylist(playlistName, playlistDescription, playlistCount, trackList)
    }

    override suspend fun deletePlaylist(playlistId: Int) {
        playlistItemRepository.deletePlaylist(playlistId)
    }
}

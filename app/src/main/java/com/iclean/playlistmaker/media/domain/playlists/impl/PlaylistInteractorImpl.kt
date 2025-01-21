package com.iclean.playlistmaker.media.domain.playlists.impl

import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.media.domain.playlists.PlaylistInteractor
import com.iclean.playlistmaker.media.domain.playlists.PlaylistRepository
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(private val playlistRepository: PlaylistRepository) : PlaylistInteractor {
    //Делаем выборку плейлистов
    override fun getPlaylists(): Flow<List<Playlist>> {
        return playlistRepository.getPlaylists()
    }
    //Добавляем трек в плейлист
    override suspend fun updatePlaylist(playlist: Playlist) {
        playlistRepository.updatePlaylist(playlist)
    }

    override suspend fun insertTrackInPlaylist(track: Track) {
        playlistRepository.insertTrackInPlaylist(track)
    }
}
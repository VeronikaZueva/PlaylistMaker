package com.iclean.playlistmaker.media.domain.impl

import com.iclean.playlistmaker.media.domain.MediaInteractor
import com.iclean.playlistmaker.media.domain.MediaRepository
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class MediaInteractorImpl(private val mediaRepository : MediaRepository) : MediaInteractor {
    override suspend fun insertTrack(track: Track) {
        mediaRepository.insertTrack(track)
    }

    override suspend fun deleteTrack(track: Track) {
        return mediaRepository.deleteTrack(track)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return mediaRepository.getFavoriteTracks()
    }

    override suspend fun onFavoriteCheck(trackId: Int): Boolean {
        return mediaRepository.onFavoriteCheck(trackId)
    }
}
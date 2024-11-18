package com.iclean.playlistmaker.media.domain

import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface MediaInteractor {
    suspend fun insertTrack(track : Track)

    suspend fun deleteTrack(track : Track)

    fun getFavoriteTracks() : Flow<List<Track>>
}
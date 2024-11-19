package com.iclean.playlistmaker.media.domain

import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    suspend fun insertTrack(track : Track)

    suspend fun deleteTrack(track : Track)

    fun getFavoriteTracks() : Flow<List<Track>>

    suspend fun onFavoriteCheck(trackId : Int) : Boolean
}
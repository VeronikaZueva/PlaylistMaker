package com.iclean.playlistmaker.media.domain.favorite

import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface MediaInteractor {
    suspend fun insertTrack(track : Track)

    suspend fun deleteTrack(id : Int)

    fun getFavoriteTracks() : Flow<List<Track>>

    suspend fun onFavoriteCheck(trackId : Int) : Boolean

    fun getFavoriteId() : List<Int>

}
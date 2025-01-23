package com.iclean.playlistmaker.media.data.favorite

import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.db.convertor.TrackDbConvertor
import com.iclean.playlistmaker.db.entity.TrackEntity
import com.iclean.playlistmaker.media.domain.favorite.MediaRepository
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class MediaRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val trackDbConvertor: TrackDbConvertor
) : MediaRepository {

    override suspend fun insertTrack(track: Track) {
        appDatabase.trackDao().insertTrack(trackDbConvertor.map(track))
    }

    override suspend fun deleteTrack(id: Int) {
        appDatabase.trackDao().deleteTrack(id)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> = flow {
        val tracks = withContext(Dispatchers.IO) {
            appDatabase.trackDao().getTracks()
        }
        emit(convertFromTrackEntity(tracks))
    }

    override suspend fun onFavoriteCheck(trackId : Int) : Boolean {
         val faviriteIdList  = withContext(Dispatchers.IO) {
            appDatabase.trackDao().getTrackIdForFavorite()
        }
        return trackId in faviriteIdList
    }

    private fun convertFromTrackEntity(tracks: List<TrackEntity>): List<Track> {
        return tracks.map { track -> trackDbConvertor.map(track) }
    }

    //Перед загрузкой активити Плеера
    override fun getFavoriteId(): List<Int> {
        return appDatabase.trackDao().getTrackIdForFavorite()
    }

}
package com.iclean.playlistmaker.search.data.impl

import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.search.domain.HistoryInt
import com.iclean.playlistmaker.search.domain.SearchHistoryInteractor
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchHistoryImpl(private val history : SearchHistoryInteractor, private val db : AppDatabase) : HistoryInt  {
    override suspend fun load() : List<Track> {
        //Получаем список id избранных треков
        val faviriteIdList  = withContext(Dispatchers.IO) {
            db.trackDao().getTrackIdForFavorite()
        }
        val historyList = history.load()
        historyList.filter { it.trackId.toInt() in faviriteIdList }.map { it.isFavorite = true}

        return historyList
    }
    override fun save(trackItem : Track) {
        return history.save(trackItem)
    }

    override fun clearHistory() {
        return history.clearHistory()
    }
}
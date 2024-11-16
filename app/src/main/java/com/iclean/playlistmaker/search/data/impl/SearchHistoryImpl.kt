package com.iclean.playlistmaker.search.data.impl

import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.search.domain.HistoryInt
import com.iclean.playlistmaker.search.domain.SearchHistoryInt
import com.iclean.playlistmaker.search.domain.models.Track

class SearchHistoryImpl(private val history : HistoryInt, private val db : AppDatabase) : SearchHistoryInt {
    override fun load() : List<Track> {
        //Получаем список id избранных треков
        val faviriteIdList = db.trackDao().getKeyId()
        val historyList = history.load()
        historyList.map{
            track -> if(track.trackId in faviriteIdList) {track.isFavorite = true}
        }
        return historyList
    }
    override fun save(trackItem : Track) {
        return history.save(trackItem)
    }

    override fun clearHistory() {
        return history.clearHistory()
    }
}
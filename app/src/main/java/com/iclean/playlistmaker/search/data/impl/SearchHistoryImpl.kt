package com.iclean.playlistmaker.search.data.impl

import com.iclean.playlistmaker.search.domain.HistoryInt
import com.iclean.playlistmaker.search.domain.SearchHistoryInt
import com.iclean.playlistmaker.search.domain.models.Track

class SearchHistoryImpl(private val history : HistoryInt) : SearchHistoryInt {
    override fun load() : List<Track> {
        return history.load()
    }
    override fun save(trackItem : Track) {
        return history.save(trackItem)
    }

    override fun clearHistory() {
        return history.clearHistory()
    }
}
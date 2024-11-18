package com.iclean.playlistmaker.search.domain.impl

import com.iclean.playlistmaker.search.domain.HistoryInt
import com.iclean.playlistmaker.search.domain.SearchHistoryInt
import com.iclean.playlistmaker.search.domain.models.Track

class HistoryInteractorImpl(private val historyRepository : HistoryInt) : SearchHistoryInt {
    override suspend fun load(): List<Track> {
        return historyRepository.load()
    }

    override fun save(trackItem: Track) {
        historyRepository.save(trackItem)
    }

    override fun clearHistory() {
        historyRepository.clearHistory()
    }
}
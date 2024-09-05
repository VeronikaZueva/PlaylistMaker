package com.iclean.playlistmaker.search.domain

import com.iclean.playlistmaker.search.domain.models.Track

interface HistoryInt {
    fun load(): List<Track>
    fun save(trackItem: Track)
    fun clearHistory()
}
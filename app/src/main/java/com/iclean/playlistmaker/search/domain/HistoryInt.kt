package com.iclean.playlistmaker.search.domain

import com.iclean.playlistmaker.search.domain.models.Track

interface HistoryInt {
    //Работа с поиском
    fun load() : List<Track>
    suspend fun save(trackItem : Track)
    fun clearHistory()
}
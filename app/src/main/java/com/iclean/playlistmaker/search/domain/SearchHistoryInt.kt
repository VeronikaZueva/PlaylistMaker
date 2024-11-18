package com.iclean.playlistmaker.search.domain

import com.iclean.playlistmaker.search.domain.models.Track

interface SearchHistoryInt {
    //Работа с поиском
    suspend fun load() : List<Track>
    fun save(trackItem : Track)
    fun clearHistory()

}
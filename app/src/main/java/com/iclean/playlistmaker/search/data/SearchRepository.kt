package com.iclean.playlistmaker.search.data

import com.iclean.playlistmaker.search.data.models.StateType
import com.iclean.playlistmaker.search.domain.models.Track

//Создадим основной интерфейс
//Наш экран имеет несколько глобальных действий: осуществлять поиск, загружать историю, сохранять трэк в историю и очищать историю

interface SearchRepository {
    fun search(expression : String) : StateType<List<Track>>

    fun load() : List<Track>
    fun save(trackItem : Track)

    fun clearHistory()
}
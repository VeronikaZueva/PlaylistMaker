package com.iclean.playlistmaker.search.domain

import com.iclean.playlistmaker.search.data.models.StateType
import com.iclean.playlistmaker.search.domain.models.Track

//Создадим основной интерфейс
//Наш экран имеет несколько глобальных действий: осуществлять поиск, загружать историю, сохранять трэк в историю и очищать историю

interface SearchRepository {
    fun search(expression : String) : StateType<List<Track>>

}
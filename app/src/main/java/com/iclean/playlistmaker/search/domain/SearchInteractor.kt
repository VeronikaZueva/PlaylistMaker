package com.iclean.playlistmaker.search.domain

import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

//Как и на других экранах, создаем интерфейс нашего интерактора
//Он будет уметь работать с историей, а потому будет содержать те же методы: load, save, и clearHistory
interface SearchInteractor {
    //Работа с поиском
    fun search(expression: String) : Flow<Pair<List<Track>?, Int?>>
}
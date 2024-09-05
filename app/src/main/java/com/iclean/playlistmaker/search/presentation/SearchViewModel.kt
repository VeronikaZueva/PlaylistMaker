package com.iclean.playlistmaker.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iclean.playlistmaker.search.domain.SearchHistoryInt
import com.iclean.playlistmaker.search.domain.SearchInteractor
import com.iclean.playlistmaker.search.domain.models.Track


class SearchViewModel(private val searchInteractor: SearchInteractor, private val historyInteractor : SearchHistoryInt) : ViewModel(){

    //Создаем нужные экземпляры data классов, с которые нам нужно будет работать во ViewModel
    private var liveData = MutableLiveData<LiveDataSearch>()

    //Работаем со всеми методами и направляем их в нужные слои
    private val consumer = object : SearchInteractor.Consumer {
        override fun consume(list: List<Track>?, error: Int?) {
            liveData.postValue(LiveDataSearch(list, error, historyInteractor.load()))
        }
    }

    //Выполняем поиск и получаем результат поиска
    fun search(expression: String) {
        searchInteractor.search(expression, consumer)
    }

    fun getResult() : LiveData<LiveDataSearch> = liveData

    //Работаем с историей:
    fun load() {
        liveData.postValue(LiveDataSearch(listOf(), -2, historyInteractor.load()))
    }

    fun save(trackItem: Track) {
        historyInteractor.save(trackItem)
    }

    fun clearHistory() {
        historyInteractor.clearHistory()
    }




}
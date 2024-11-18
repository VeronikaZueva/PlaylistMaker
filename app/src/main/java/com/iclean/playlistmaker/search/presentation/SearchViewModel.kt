package com.iclean.playlistmaker.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iclean.playlistmaker.search.domain.SearchHistoryInt
import com.iclean.playlistmaker.search.domain.SearchInteractor
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchViewModel(private val searchInteractor: SearchInteractor,
                      private val historyInteractor : SearchHistoryInt
) : ViewModel(){

    companion object {
        private const val DELAY = 2000L
    }

    private var latestSearchText : String? = null
    private var searchJob : Job? = null

    //Создаем нужные экземпляры data классов, с которые нам нужно будет работать во ViewModel
    private var liveData = MutableLiveData<LiveDataSearch>()

    //Выполняем поиск и получаем результат поиска
    fun search(expression: String) {
        if(expression == latestSearchText) {
            return
        }

        latestSearchText = expression

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(DELAY)
            searchRequest(expression)
        }
    }

    fun getResult() : LiveData<LiveDataSearch> = liveData

    //Работаем с историей:
     fun load() {
         viewModelScope.launch {
             renderState(LiveDataSearch(listOf(), -2, historyInteractor.load()))
         }

    }

    fun save(trackItem: Track) {
        historyInteractor.save(trackItem)
    }

    fun clearHistory() {
        historyInteractor.clearHistory()
    }

    //Внутренние методы класса
    private fun searchRequest(newExpression : String) {
        if(newExpression.isNotEmpty()) {
            viewModelScope.launch {
                searchInteractor
                    .search(newExpression)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }

    private fun processResult(foundExpression : List<Track>?, errorCode: Int?) {
        val tracks = mutableListOf<Track>()
        if(foundExpression != null) {
            tracks.addAll(foundExpression)
        }

        when {
            errorCode != null -> {
                renderState(LiveDataSearch(listOf(), -1, listOf()))
            }
            else -> {
                renderState(LiveDataSearch(tracks, 200, listOf()))
            }
        }
    }

    private fun renderState(state : LiveDataSearch) {
        liveData.postValue(state)
    }


}
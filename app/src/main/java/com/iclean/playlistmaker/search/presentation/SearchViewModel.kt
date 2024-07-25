package com.iclean.playlistmaker.search.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iclean.playlistmaker.general.Creator
import com.iclean.playlistmaker.search.data.dto.HandlerController
import com.iclean.playlistmaker.search.domain.SearchInteractor
import com.iclean.playlistmaker.search.domain.models.Track


class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel(){

   companion object {
       fun getViewModelFactory(sharedPreferences: SharedPreferences, context: Context) : ViewModelProvider.Factory =
           object : ViewModelProvider.Factory {
               @Suppress("UNCHECKED_CAST")
               override fun<T : ViewModel> create(modelClass: Class<T>) : T {
                   return SearchViewModel(Creator.getSearchInteractor(sharedPreferences, context)) as T
               }
           }
       private const val DELAY = 2000L
   }

    //Создаем нужные экземпляры data классов, с которые нам нужно будет работать во ViewModel
    private val handlerController = HandlerController()
    private val liveData = MutableLiveData<LiveDataSearch>()

    //Работаем со всеми методами и направляем их в нужные слои
    private val consumer = object : SearchInteractor.Consumer {
        override fun consume(list: List<Track>?, error: Int?) {
            liveData.postValue(LiveDataSearch(list, error, searchInteractor.load()))
        }
    }

    //Выполняем поиск и получаем результат поиска
    fun search(expression: String) {
        searchInteractor.search(expression, consumer)
    }

    fun getResult() : LiveData<LiveDataSearch> = liveData

    //Работаем с историей:
    fun load() {
        liveData.postValue(LiveDataSearch(listOf(), -2, searchInteractor.load()))
    }

    fun save(trackItem: Track) {
        searchInteractor.save(trackItem)
    }

    fun clearHistory() {
        searchInteractor.clearHistory()
    }

    //Работаем с Handler
    fun postDelay(runnable: Runnable) {
        handlerController.postDelay(runnable, DELAY)
    }

    fun removeCallback(runnable: Runnable) {
        handlerController.removeCallback(runnable)
    }




}
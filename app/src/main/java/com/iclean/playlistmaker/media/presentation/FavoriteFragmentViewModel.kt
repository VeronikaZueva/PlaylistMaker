package com.iclean.playlistmaker.media.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iclean.playlistmaker.media.domain.MediaInteractor
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.launch

class FavoriteFragmentViewModel(private val favoriteInteractor : MediaInteractor) : ViewModel() {

    private val liveData = MutableLiveData<LiveDataFavoriteState>()

    fun getLiveData() : LiveData<LiveDataFavoriteState> {
        return liveData
    }

    fun returnFavoriteTracks() {
        viewModelScope.launch {
            favoriteInteractor.getFavoriteTracks()
                .collect { tracksList ->
                    renderResults(tracksList)
                }
        }
    }

    private fun renderResults(trackList : List<Track>) {
        if(trackList.isEmpty()) {
            //Отображаем плейсхолдер
            liveData.postValue(LiveDataFavoriteState(true))
        } else {
            liveData.postValue(LiveDataFavoriteState(false))
        }
    }

}
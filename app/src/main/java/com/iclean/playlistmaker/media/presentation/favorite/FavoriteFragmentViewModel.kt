package com.iclean.playlistmaker.media.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iclean.playlistmaker.media.domain.favorite.MediaInteractor
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.launch

class FavoriteFragmentViewModel(private val favoriteInteractor : MediaInteractor) : ViewModel() {

    private val liveData = MutableLiveData<LiveDataFavoriteState>()

    fun getLiveData() : LiveData<LiveDataFavoriteState> = liveData

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
            liveData.postValue(LiveDataFavoriteState(emptyList(), 1))
        } else {
            liveData.postValue(LiveDataFavoriteState(trackList, null))
        }
    }

}
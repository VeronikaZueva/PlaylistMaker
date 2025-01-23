package com.iclean.playlistmaker.playlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iclean.playlistmaker.playlist.domain.PlaylistItemInteractor
import kotlinx.coroutines.launch

class PlaylistItemViewModel(private val playlistItemInteractor: PlaylistItemInteractor) : ViewModel() {

        private val liveData = MutableLiveData<PlaylistItemState>()

        fun getLiveData() : LiveData<PlaylistItemState> = liveData


        fun getPlaylistFromId(id : Int)  {
            viewModelScope.launch {
                playlistItemInteractor.getPlaylistFromId(id)
                   .collect {
                       playlist ->liveData.postValue(PlaylistItemState.PlaylistItemLiveData(playlist))
                   }
            }
        }


}
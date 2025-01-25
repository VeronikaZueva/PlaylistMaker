package com.iclean.playlistmaker.playlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iclean.playlistmaker.playlist.domain.PlaylistItemInteractor
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.launch

class PlaylistItemViewModel(private val playlistItemInteractor: PlaylistItemInteractor) : ViewModel() {

        private val liveDataForPlaylist = MutableLiveData<LiveDataForPlaylist>()
        private val liveDataForTracklist = MutableLiveData<LivaDataForTracklist>()

        fun getLiveDataPlaylist() : LiveData<LiveDataForPlaylist> = liveDataForPlaylist
        fun getLiveDataTracklist() : LiveData<LivaDataForTracklist> = liveDataForTracklist


        fun getPlaylistFromId(id : Int)  {
            viewModelScope.launch {
                playlistItemInteractor.getPlaylistFromId(id)
                   .collect {
                       playlist ->liveDataForPlaylist.postValue(LiveDataForPlaylist(playlist))
                   }
            }
        }


    fun getTracksForPlaylist(trackList: List<Int>?)  {
            viewModelScope.launch {
                playlistItemInteractor.getTracksForPlaylist(trackList)
                    .collect {
                        results -> renderResult(results)
                    }
            }
        }

    private fun renderResult(result : List<Track>) {
        if(result.isEmpty()) {
            liveDataForTracklist.postValue(LivaDataForTracklist(emptyList(), 1))
        } else {
            liveDataForTracklist.postValue(LivaDataForTracklist(result, null))
        }
    }






}
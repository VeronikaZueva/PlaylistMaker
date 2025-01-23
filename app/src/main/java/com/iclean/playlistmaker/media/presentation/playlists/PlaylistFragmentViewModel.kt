package com.iclean.playlistmaker.media.presentation.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.media.domain.playlists.PlaylistInteractor
import kotlinx.coroutines.launch

class PlaylistFragmentViewModel(private val playlistInteractor: PlaylistInteractor) : ViewModel() {

    private val liveData = MutableLiveData<LiveDataPlaylistState>()

    fun getLiveData() : LiveData<LiveDataPlaylistState> = liveData

    fun returnPlaylists() {
        viewModelScope.launch {
            playlistInteractor.getPlaylists()
                .collect{
                    playlists -> renderResults(playlists)
                }
        }
    }

    private fun renderResults(playlists : List<Playlist>) {
        if(playlists.isEmpty()) {
            liveData.postValue(LiveDataPlaylistState(emptyList(), 1))
        } else {
            liveData.postValue(LiveDataPlaylistState(playlists, null))
        }
    }

}
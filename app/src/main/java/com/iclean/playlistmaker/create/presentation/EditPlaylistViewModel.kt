package com.iclean.playlistmaker.create.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iclean.playlistmaker.create.domain.CreatePlaylistInteractor
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.media.domain.playlists.PlaylistInteractor
import com.iclean.playlistmaker.playlist.domain.PlaylistItemInteractor
import com.iclean.playlistmaker.playlist.presentation.LiveDataForPlaylist
import kotlinx.coroutines.launch

class EditPlaylistViewModel(
    createPlaylistInteractor: CreatePlaylistInteractor,
    private val playlistItemInteractor : PlaylistItemInteractor,
    private val playlistInteractor: PlaylistInteractor
    ) : CreatePlaylistViewModel(createPlaylistInteractor) {

    private val liveDataForPlaylist = MutableLiveData<LiveDataForPlaylist>()

    fun getLiveDataPlaylist() : LiveData<LiveDataForPlaylist> = liveDataForPlaylist

    fun getPlaylistFromId(id : Int)  {
        viewModelScope.launch {
            playlistItemInteractor.getPlaylistFromId(id)
                .collect {
                        playlist ->liveDataForPlaylist.postValue(LiveDataForPlaylist(playlist))
                }
        }
    }

    fun updatePlaylist(playlist : Playlist) {
        viewModelScope.launch { playlistInteractor.updatePlaylist(playlist)  }
    }

}
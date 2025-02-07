package com.iclean.playlistmaker.playlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.media.domain.playlists.PlaylistInteractor
import com.iclean.playlistmaker.playlist.domain.PlaylistItemInteractor
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistItemViewModel(
    private val playlistItemInteractor: PlaylistItemInteractor,
    private val playlistInteractor: PlaylistInteractor) : ViewModel() {

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


    fun getTracksForPlaylist(trackList: String)  {
            viewModelScope.launch {
                val results = playlistItemInteractor.getTracksForPlaylist(trackList)
                renderResult(results)
                }
        }

    private fun renderResult(result : List<Track>) {
        if(result.isEmpty()) {
            liveDataForTracklist.postValue(LivaDataForTracklist(emptyList(), 1, 0))
        } else {
            val timeList = result.map { track ->
                track.trackTimeMillis.toInt()
            }
            val sum = timeList.sum()

            liveDataForTracklist.postValue(LivaDataForTracklist(result.reversed(), null, sum))
        }
    }

    fun updatePlaylist(playlist : Playlist, tracklists : String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistInteractor.updatePlaylist(playlist)
        }
        getTracksForPlaylist(tracklists)
    }
    suspend fun checkTrackAllPlaylists(track : Int, playlistId : Int) {
        playlistItemInteractor.checkTrackAllPlaylists(track, playlistId)
    }

    fun share(playlistName : String, playlistDescription : String?, playlistCount : Int, trackList : List<Track>) {
        playlistItemInteractor.sharePlaylist(playlistName, playlistDescription, playlistCount, trackList)
    }

    fun deletePlaylist(playlistId : Int) {
        viewModelScope.launch { playlistItemInteractor.deletePlaylist(playlistId) }
    }


}
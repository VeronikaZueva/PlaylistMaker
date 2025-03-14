package com.iclean.playlistmaker.player.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.media.domain.favorite.MediaInteractor
import com.iclean.playlistmaker.media.domain.playlists.PlaylistInteractor
import com.iclean.playlistmaker.player.domain.OnCompletionListener
import com.iclean.playlistmaker.player.domain.OnPreparedListener
import com.iclean.playlistmaker.player.domain.PlayerInteractor
import com.iclean.playlistmaker.player.domain.models.MediaPlayerState
import com.iclean.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//Здесь тоже упрощаем логику. Начнем с того, что явно делает интерактор

class PlayerViewModel(private val playerInteractor: PlayerInteractor,
                      private val favoriteInteractor: MediaInteractor,
                      private val playlistInteractor : PlaylistInteractor
) : ViewModel() {

    companion object {
        private const val DELAY = 1000L
    }

    //Подключаем дополнительные классы и задаем начальные переменные
     private var playerState = MediaPlayerState.STATE_DEFAULT

    //Задаем LiveData
    private val liveData = MutableLiveData<LiveDataPlayer>()
    private val liveDataPlaylist = MutableLiveData<LiveDataPlaylist>()



    //Добавляем Job
    private var job : Job? = null
    //Инициализируем переменные, которые потребуются позже. в ходе обработки сценария
    private lateinit var gson : Track

    //Главный обработчик
    fun buttonCheck() : Boolean {
        when(playerState) {
            MediaPlayerState.STATE_PLAYING -> {
                pausePlayer()
                playerState = MediaPlayerState.STATE_PAUSED
                return true
            }
            MediaPlayerState.STATE_PREPARED, MediaPlayerState.STATE_PAUSED -> {
                startPlayer()
                playerState = MediaPlayerState.STATE_PLAYING
                startTimer()
                return false
            }
            else -> {
                return false
            }
        }
    }



    //Методы управления воспроизведением музыки
    fun preparePlayer(previewUrl : String) {playerInteractor.preparePlayer(previewUrl)}

    fun setOnPreparedListener(listener: OnPreparedListener) {
        playerInteractor.setOnPreparedListener(listener)
        playerState = MediaPlayerState.STATE_PREPARED
    }
    fun setOnCompletionListener(listener: OnCompletionListener) {
        playerInteractor.setOnCompletionListener(listener)
        playerState = MediaPlayerState.STATE_PREPARED
    }

    fun pausePlayer() {
        playerInteractor.pausePlayer()
        playerState = MediaPlayerState.STATE_PAUSED
    }
    private fun startPlayer() {playerInteractor.startPlayer()}
    fun release() {playerInteractor.release()}


    fun getTrack(trackBundle: String): LiveData<LiveDataPlayer> {
        gson = Gson().fromJson(trackBundle, Track::class.java)

        liveData.value = LiveDataPlayer(gson, 0)
        return liveData
    }

    fun checkFavoriteState(trackId: Int) : Boolean {
        return trackId in favoriteInteractor.getFavoriteId()
    }

    fun isPlaying() : Boolean {
        return playerInteractor.isPlaying()
    }


    //Работаем с таймером
    fun stopTimer() {
        job?.cancel()
    }
    private fun getCurrentPosition() : Int {
        return playerInteractor.getCurrentPosition()
    }

    private fun startTimer() {
        job = viewModelScope.launch {
            while(getStatus()) {
                delay(DELAY)
                liveData.postValue(LiveDataPlayer(gson, getCurrentPosition().toLong()))
            }
        }

    }

    private fun getStatus() : Boolean {
        return playerState == MediaPlayerState.STATE_PLAYING
    }

    fun setPreparedState() {
        playerState = MediaPlayerState.STATE_PREPARED
    }

    //Избранные треки
    @SuppressLint("SuspiciousIndentation")
    fun onFavoriteClicked(track : Track) {
        viewModelScope.launch {
        val newTrack = track.copy(isFavorite = !track.isFavorite)
            if((track.isFavorite)) {
                favoriteInteractor.deleteTrack(track.trackId.toInt())
            } else {
                favoriteInteractor.insertTrack(newTrack)

            }
            liveData.postValue(LiveDataPlayer(newTrack, getCurrentPosition().toLong()))


        }

    }

    //Плейлисты
    fun getLiveDataPlaylist() : LiveData<LiveDataPlaylist> = liveDataPlaylist

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
            liveDataPlaylist.postValue(LiveDataPlaylist(emptyList(), 1))
        } else {
            liveDataPlaylist.postValue(LiveDataPlaylist(playlists, null))
        }
    }

    //Добавляем трек в плейлист
    fun updatePlaylist(playlist : Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistInteractor.updatePlaylist(playlist)
        }
    }
    fun insertTrackInPlaylist(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistInteractor.insertTrackInPlaylist(track)
        }
    }



}

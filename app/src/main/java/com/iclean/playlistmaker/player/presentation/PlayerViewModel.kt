package com.iclean.playlistmaker.player.presentation

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.iclean.playlistmaker.general.Creator
import com.iclean.playlistmaker.player.domain.PlayerInteractor
import com.iclean.playlistmaker.player.domain.models.MediaPlayerState
import com.iclean.playlistmaker.search.domain.models.Track

//Здесь тоже упрощаем логику. Начнем с того, что явно делает интерактор

class PlayerViewModel : ViewModel() {
    //В данном случае интерактор будем определять не в конструкторе, т.к. при запуске плеера, нам нужно будет его изменить
    private lateinit var playerInteractor: PlayerInteractor
    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlayerViewModel() as T
                }
            }
        private const val DELAY = 1000L
    }

    //Подключаем дополнительные классы и задаем начальные переменные
    private var playerState = MediaPlayerState.STATE_DEFAULT
    //Получаем данные по треку из интента
    private val liveData = MutableLiveData<LiveDataPlayer>()

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
                postTimerDelay()
                return false
            }
            else -> {
                return false
            }
        }
    }

    //Методы управления воспроизведением музыки
    fun preparePlayer() {playerInteractor.preparePlayer()}
    fun setOnPreparedListener(onPrepared: () -> Unit) {
       onPrepared()
        playerInteractor.setOnPreparedListener()
        playerState = MediaPlayerState.STATE_PREPARED
    }
    fun setOnCompletionListener(onCompletion : () -> Unit) {
        onCompletion()
        playerInteractor.setOnCompletionListener()
        playerState = MediaPlayerState.STATE_PREPARED
    }

    fun pausePlayer() {playerInteractor.pausePlayer()}
    private fun startPlayer() {playerInteractor.startPlayer()}
    fun release() {playerInteractor.release()}


    fun getTrack(intent: Intent): LiveData<LiveDataPlayer> {
        val gson = Gson().fromJson(intent.extras?.getString("trackObject"), Track::class.java)
        liveData.value = LiveDataPlayer(gson, 0)
        playerInteractor = Creator.getPlayerInteractor(gson.previewUrl, startTimer(gson))
        return liveData
    }


    //Работаем с Handler
    private fun postTimerDelay() {
        playerInteractor.postTimerDelay(DELAY)
    }
    fun removeCallback() {
        playerInteractor.removeCallback()
    }
    fun removeCallbacksAndMessages() {
        playerInteractor.removeCallbacksAndMessages()
    }


    //Работаем с таймером
    private fun getCurrentPosition() : Int {
        return playerInteractor.getCurrentPosition()
    }

    private fun startTimer(track: Track) : Runnable {
        return Runnable {
            if(playerState==MediaPlayerState.STATE_PLAYING) {
                postTimerDelay()
                liveData.value = LiveDataPlayer(track, getCurrentPosition().toLong())
            }
        }
    }



}

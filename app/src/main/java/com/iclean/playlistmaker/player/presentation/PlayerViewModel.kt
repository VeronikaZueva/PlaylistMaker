package com.iclean.playlistmaker.player.presentation

import android.content.Intent
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.iclean.playlistmaker.general.Creator
import com.iclean.playlistmaker.player.domain.PlayerInteractor
import com.iclean.playlistmaker.player.data.dto.HandlerController
import com.iclean.playlistmaker.player.domain.models.MediaPlayerState
import com.iclean.playlistmaker.search.domain.models.Track

//Здесь тоже упрощаем логику. Начнем с того, что явно делает интерактор

class PlayerViewModel : ViewModel() {
    //В данном случае интерактор будем определять не в конструкторе, т.к. при запуске плеера, нам нужно будет его изменить
    private lateinit var playerInteractor: PlayerInteractor
    companion object {
        fun getViewModelFactory() : ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun<T : ViewModel> create(modelClass: Class<T>) : T {
                    return PlayerViewModel as T
                }
            }
        private const val DELAY = 1000L
    }

    //Подключаем дополнительные классы и задаем начальные переменные
    private var playerState = MediaPlayerState.STATE_DEFAULT

    //Методы управления воспроизведением музыки

    fun setOnPreparedListener(listener : MediaPlayer.OnPreparedListener) {
        playerInteractor.setOnPreparedListener(listener)
    }
    fun setOnCompletionListener(listener : MediaPlayer.OnCompletionListener) {
        playerInteractor.setOnCompletionListener(listener)
    }
    fun preparePlayer() {playerInteractor.preparePlayer()}
    fun startPlayer() {playerInteractor.startPlayer()}
    fun pausePlayer() {playerInteractor.pausePlayer()}
    fun release() {playerInteractor.release()}

    //Получаем данные по треку из интента
    private val liveData = MutableLiveData<LiveDataPlayer>()
    fun getTrack(intent: Intent): LiveData<LiveDataPlayer> {

        val gson = Gson().fromJson(intent.extras?.getString("track"), Track::class.java)
        liveData.value = LiveDataPlayer(gson, 0)
        playerInteractor = Creator.getPlayerInteractor(gson.previewUrl, startTimer(gson))
        return liveData
    }


    //Работаем с Handler
    private val handlerController = HandlerController()
    fun postDelay(runnable : Runnable) {
        handlerController.postDelay(runnable, DELAY)
    }
    private fun postTimerDelay() {
        handlerController.postTimerDelay(DELAY)
    }
    fun removeCallback() {
        handlerController.removeCallback()
    }
    fun removeCallbacksAndMessages() {
        handlerController.removeCallbacksAndMessages()
    }

    //Переключаем кнопку play|pause
    fun buttonCheck() : Boolean {
        return when(playerState) {
            MediaPlayerState.STATE_PLAYING -> {
                playerInteractor.pausePlayer()
                playerState = MediaPlayerState.STATE_PAUSED
                true
            }
            MediaPlayerState.STATE_PAUSED -> {
                playerInteractor.startPlayer()
                playerState = MediaPlayerState.STATE_PLAYING
                false
            }
            else -> false
        }

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

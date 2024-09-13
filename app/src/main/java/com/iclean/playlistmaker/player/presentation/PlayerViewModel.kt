package com.iclean.playlistmaker.player.presentation

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iclean.playlistmaker.player.domain.OnCompletionListener
import com.iclean.playlistmaker.player.domain.OnPreparedListener
import com.iclean.playlistmaker.player.domain.PlayerInteractor
import com.iclean.playlistmaker.player.domain.models.MediaPlayerState
import com.iclean.playlistmaker.search.domain.models.Track

//Здесь тоже упрощаем логику. Начнем с того, что явно делает интерактор

class PlayerViewModel(private val playerInteractor: PlayerInteractor) : ViewModel() {

    companion object {
        private const val DELAY = 1000L
    }

    //Подключаем дополнительные классы и задаем начальные переменные
    private var playerState = MediaPlayerState.STATE_DEFAULT
    //Получаем данные по треку из интента
    private val liveData = MutableLiveData<LiveDataPlayer>()
    //Инициализируем переменные, которые потребуются позже. в ходе обработки сценария
    private lateinit var runnable: Runnable
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
                postTimerDelay()
                return false
            }
            else -> {
                return false
            }
        }
    }

    //Методы управления воспроизведением музыки
    fun preparePlayer() {playerInteractor.preparePlayer(gson.previewUrl)}
    fun setOnPreparedListener(listener: OnPreparedListener) {
        playerInteractor.setOnPreparedListener(listener)
        playerState = MediaPlayerState.STATE_PREPARED
    }
    fun setOnCompletionListener(listener: OnCompletionListener) {
        playerInteractor.setOnCompletionListener(listener)
        playerState = MediaPlayerState.STATE_PREPARED
    }

    fun pausePlayer() {playerInteractor.pausePlayer()}
    private fun startPlayer() {playerInteractor.startPlayer()}
    fun release() {playerInteractor.release()}


    fun getTrack(intent: Intent): LiveData<LiveDataPlayer> {
        gson = Gson().fromJson(intent.extras?.getString("trackObject"), Track::class.java)
        runnable = startTimer((gson))
        liveData.value = LiveDataPlayer(gson, 0)
        return liveData
    }


    //Работаем с Handler
    private fun postTimerDelay() {
        playerInteractor.postTimerDelay(runnable, DELAY)
    }
    fun removeCallback() {
        playerInteractor.removeCallback(runnable)
    }
    fun removeCallbacksAndMessages() {
        playerInteractor.removeCallbacksAndMessages(runnable)
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

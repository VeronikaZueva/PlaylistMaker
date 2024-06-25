package com.iclean.playlistmaker.player.domain


import com.iclean.playlistmaker.player.domain.models.MediaPlayerState

class TrackMediaPlayerImpl {


    //Функции состояния воспроизведения
    fun defaultPlayerState() : MediaPlayerState {
        return MediaPlayerState.STATE_DEFAULT
    }

    fun startPlayer() : MediaPlayerState {
       return MediaPlayerState.STATE_PLAYING
    }
    fun pausePlayer() : MediaPlayerState {
       return MediaPlayerState.STATE_PAUSED
    }

    fun statusTimer() : String {
        return "00:00"
    }


}
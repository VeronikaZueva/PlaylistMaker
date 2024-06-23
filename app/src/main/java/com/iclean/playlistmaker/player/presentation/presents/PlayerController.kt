package com.iclean.playlistmaker.player.presentation.presents


import com.iclean.playlistmaker.data.models.MediaPlayerState


class PlayerController {
    //Определяем значение флага воспроизведения музыки
       fun playControl(state : MediaPlayerState) : Boolean {
           return when (state) {
               MediaPlayerState.STATE_PLAYING -> true
               else -> false
           }
       }
    //Определяем значение флага для работы таймера
        fun timerControl(state : MediaPlayerState) : Boolean {
            return when (state) {
                MediaPlayerState.STATE_PLAYING -> true
                else -> false
            }
        }
    //Определяем значение флага - завершено ли воспроизведение
    fun completedControl(state : MediaPlayerState) : Boolean {
        return when (state) {
            MediaPlayerState.STATE_COMPLETED,
            MediaPlayerState.STATE_PREPARED,
            MediaPlayerState.STATE_DEFAULT,
            MediaPlayerState.STATE_PAUSED -> true
            else -> false
        }
    }


}
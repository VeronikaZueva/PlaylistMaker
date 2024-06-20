package com.iclean.playlistmaker.player.presentation.presents


import com.iclean.playlistmaker.data.models.MediaPlayerState


class PlayerController {
       fun playControl(state : MediaPlayerState) : Boolean {
           return when (state) {
               MediaPlayerState.STATE_PLAYING -> true
               else -> false
           }
       }

        fun timerControl(state : MediaPlayerState) : Boolean {
            return when (state) {
                MediaPlayerState.STATE_PAUSED -> true
                else -> false
            }
        }



}
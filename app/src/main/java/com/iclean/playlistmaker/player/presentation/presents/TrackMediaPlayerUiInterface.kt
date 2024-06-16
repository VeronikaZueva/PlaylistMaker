package com.iclean.playlistmaker.player.presentation.presents

import android.widget.ImageButton

interface TrackMediaPlayerUiInterface {

    fun startPlayer(playButton : ImageButton) {}
    fun pausePlayer(playButton : ImageButton) {}

}
package com.iclean.playlistmaker.player.presentation.ui

import android.widget.ImageButton
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.player.presentation.presents.TrackMediaPlayerUiInterface

class TrackMediaPlayerUi : TrackMediaPlayerUiInterface {

    override fun startPlayer(playButton : ImageButton) {
        playButton.setImageResource(R.drawable.pause)
    }

    override fun pausePlayer(playButton : ImageButton) {
        playButton.setImageResource(R.drawable.play)
    }



}
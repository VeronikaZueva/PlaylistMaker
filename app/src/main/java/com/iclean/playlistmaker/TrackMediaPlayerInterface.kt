@file:Suppress("UNREACHABLE_CODE")

package com.iclean.playlistmaker

import android.media.MediaPlayer
import android.widget.ImageButton

interface TrackMediaPlayerInterface {
    var playButton : ImageButton
    var url : String
    var playerState : Int
    val mediaPlayer : MediaPlayer
    val trackMethods : TrackMethods

    fun preparePlayer(url : String) {}

    fun startPlayer() {}

    fun pausePlayer() {}

    fun playControl() {}

    fun statusTimer(timerState : Int): String? {
        return TODO("Provide the return value")
    }
}
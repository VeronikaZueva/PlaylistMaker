package com.iclean.playlistmaker.player.data.repository

import android.media.MediaPlayer
import android.os.Handler
import com.iclean.playlistmaker.player.domain.OnCompletionListener
import com.iclean.playlistmaker.player.domain.OnPreparedListener
import com.iclean.playlistmaker.player.domain.PlayerRepository


class PlayerRepositoryImpl(private val mediaPlayer : MediaPlayer,
                           private val handler : Handler) :
    PlayerRepository {

    //Медиаплеер
    override fun setOnPreparedListener(listener: OnPreparedListener) {
        mediaPlayer.setOnPreparedListener {  listener.onPrepared() }
    }
    override fun setOnCompletionListener(listener: OnCompletionListener) {
        mediaPlayer.setOnCompletionListener { listener.onCompletion() }
    }
    override fun preparePlayer(previewUrl : String) {
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
    }
    override fun startPlayer() {
        mediaPlayer.start()
    }

    override fun pausePlayer(){
        mediaPlayer.pause()
    }

    override fun release() {mediaPlayer.release()}

    override fun getCurrentPosition() : Int {
        return mediaPlayer.currentPosition
    }

    //Handler
    override fun postTimerDelay(runnable : Runnable, delay: Long) {
        handler.postDelayed(runnable, delay)
    }

    override fun removeCallback(runnable : Runnable) {
        handler.removeCallbacks(runnable)
    }

    override fun removeCallbacksAndMessages(runnable : Runnable) {
        handler.removeCallbacksAndMessages(runnable)
    }

}









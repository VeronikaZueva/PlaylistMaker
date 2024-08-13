package com.iclean.playlistmaker.player.domain.impl


import com.iclean.playlistmaker.player.domain.PlayerInteractor
import com.iclean.playlistmaker.player.domain.PlayerRepository


class PlayerInteractorImpl(private val player : PlayerRepository) : PlayerInteractor {
    //Медиаплеер
    override fun setOnPreparedListener() {
        player.setOnPreparedListener()
    }
    override fun setOnCompletionListener() {
        player.setOnCompletionListener()
    }

    override fun preparePlayer()  {
        player.preparePlayer()
    }


    override fun startPlayer()  {
        player.startPlayer()
    }

    override fun pausePlayer()  {
        player.pausePlayer()
    }

    override fun release() {
        player.release()
    }

    override fun getCurrentPosition() : Int {
        return player.getCurrentPosition()
    }

    //Handler
    override fun postTimerDelay(delay : Long) {
        player.postTimerDelay(delay)
    }

    override fun removeCallback() {
        player.removeCallback()
    }

    override fun removeCallbacksAndMessages() {
        player.removeCallbacksAndMessages()
    }

}
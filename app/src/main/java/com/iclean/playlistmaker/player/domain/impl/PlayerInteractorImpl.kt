package com.iclean.playlistmaker.player.domain.impl



import com.iclean.playlistmaker.data.models.MediaPlayerState
import com.iclean.playlistmaker.player.domain.interactor.PlayerInteractor
import com.iclean.playlistmaker.player.domain.repository.PlayerRepository


class PlayerInteractorImpl(private val player : PlayerRepository) : PlayerInteractor {

    override fun getPlayerState(): MediaPlayerState {
        return player.state
    }
    override fun preparePlayer(previewUrl : String)  {
        player.preparePlayer(previewUrl)
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

    override fun statusTimer(statePlayer : MediaPlayerState) : String? {
        return player.statusTimer(statePlayer)
    }



}
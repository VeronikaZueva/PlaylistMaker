package com.iclean.playlistmaker.player.domain.impl

import com.iclean.playlistmaker.data.repository.PlayerRepositoryImpl
import com.iclean.playlistmaker.player.domain.interactor.PlayerInteractor
import com.iclean.playlistmaker.player.domain.models.MediaPlayerState


class PlayerInteractorImpl(private var player: PlayerRepositoryImpl) : PlayerInteractor {
    private var state = player.getDefaultState()
    override fun preparePlayer(url : String) : MediaPlayerState {
        state = player.preparePlayer(url)
        return state
    }

    override fun playControl(statePlayer : MediaPlayerState) : Boolean {
        return when(statePlayer) {
            MediaPlayerState.STATE_PLAYING -> {
                state = player.startPlayer()
                false
            }

            else -> {
                state = player.pausePlayer()
                true
            }
        }

    }

    override fun startPlayer(isEnable : Boolean) : MediaPlayerState {
        state =  player.getState(isEnable)
            return state
    }

    override fun pausePlayer(isEnable : Boolean) : MediaPlayerState {
        state =  player.getState(isEnable)
        return state
    }

    override fun statusTimer(statePlayer : MediaPlayerState) : String? {
        return player.statusTimer(statePlayer)
    }

}
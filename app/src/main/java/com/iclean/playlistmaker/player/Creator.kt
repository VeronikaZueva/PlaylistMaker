package com.iclean.playlistmaker.player

import com.iclean.playlistmaker.player.data.repository.PlayerRepositoryImpl
import com.iclean.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.iclean.playlistmaker.player.domain.interactor.PlayerInteractor
import com.iclean.playlistmaker.player.domain.repository.PlayerRepository

object Creator {
    private fun getPlayerRepository() : PlayerRepository {
        return PlayerRepositoryImpl()
    }
    fun getPlayerInteractor() : PlayerInteractor {
        return PlayerInteractorImpl(player = getPlayerRepository())
    }
}
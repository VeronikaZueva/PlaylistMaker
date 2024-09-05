package com.iclean.playlistmaker.player.di

import com.iclean.playlistmaker.player.domain.PlayerInteractor
import com.iclean.playlistmaker.player.domain.impl.PlayerInteractorImpl
import org.koin.dsl.module

val playerInteractorModule = module {
    single<PlayerInteractor> {
        PlayerInteractorImpl(get())
    }
}
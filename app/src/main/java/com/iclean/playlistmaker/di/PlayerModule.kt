package com.iclean.playlistmaker.di

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.iclean.playlistmaker.player.data.repository.PlayerRepositoryImpl
import com.iclean.playlistmaker.player.domain.PlayerInteractor
import com.iclean.playlistmaker.player.domain.PlayerRepository
import com.iclean.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.iclean.playlistmaker.player.presentation.PlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playerModule = module {
    single<PlayerRepository> {
        PlayerRepositoryImpl(mediaPlayer = get(), handler = get())
    }
    factory {
        MediaPlayer()
    }
    single {
        Handler(Looper.getMainLooper())
    }
    factory<PlayerInteractor> {
        PlayerInteractorImpl(player = get())
    }
    viewModel {
        PlayerViewModel(playerInteractor = get())
    }
}
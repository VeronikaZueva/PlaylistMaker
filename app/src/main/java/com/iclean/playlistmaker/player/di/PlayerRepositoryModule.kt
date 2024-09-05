package com.iclean.playlistmaker.player.di

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.iclean.playlistmaker.player.data.repository.PlayerRepositoryImpl
import com.iclean.playlistmaker.player.domain.PlayerRepository
import org.koin.dsl.module

val playerRepositoryModule = module {

    single<PlayerRepository> {
        PlayerRepositoryImpl(get(), get())
    }
    factory {
        MediaPlayer()
    }
    single {
        Handler(Looper.getMainLooper())
    }

}

package com.iclean.playlistmaker.player.di

import com.iclean.playlistmaker.player.presentation.PlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val playerViewModel = module {
    viewModel {
        PlayerViewModel(get())
    }
}
package com.iclean.playlistmaker.di

import com.iclean.playlistmaker.media.presentation.FavoriteFragmentViewModel
import com.iclean.playlistmaker.media.presentation.PlaylistFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mediaModule = module {
    viewModel {
        FavoriteFragmentViewModel()
    }
    viewModel {
        PlaylistFragmentViewModel()
    }
}
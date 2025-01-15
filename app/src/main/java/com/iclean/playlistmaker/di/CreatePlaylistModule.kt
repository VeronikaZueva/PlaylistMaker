package com.iclean.playlistmaker.di

import com.iclean.playlistmaker.create.presentation.CreatePlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val createPlaylistModule = module {
        viewModel {
            CreatePlaylistViewModel()
        }
    }


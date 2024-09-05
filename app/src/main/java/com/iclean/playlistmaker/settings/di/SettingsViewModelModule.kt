package com.iclean.playlistmaker.settings.di

import com.iclean.playlistmaker.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsViewModelModule = module {
    viewModel {
        SettingsViewModel(get(), get())
    }
}
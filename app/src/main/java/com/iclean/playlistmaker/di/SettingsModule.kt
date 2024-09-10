package com.iclean.playlistmaker.di

import com.iclean.playlistmaker.settings.data.impl.SettingsRepositoryImpl
import com.iclean.playlistmaker.settings.domain.SettingsInteractor
import com.iclean.playlistmaker.settings.domain.SettingsRepository
import com.iclean.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.iclean.playlistmaker.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    single<SettingsRepository> {
        SettingsRepositoryImpl(context = get())
    }
    factory<SettingsInteractor> {
        SettingsInteractorImpl(repository = get())
    }
    viewModel {
        SettingsViewModel(settingsInteractor = get(), sharingInteractor = get(), application = get())
    }
}
package com.iclean.playlistmaker.settings.di

import com.iclean.playlistmaker.settings.domain.SettingsInteractor
import com.iclean.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import org.koin.dsl.module

val settingInteractorModule = module {
    single<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }
}
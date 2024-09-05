package com.iclean.playlistmaker.settings.di

import android.content.Context
import com.iclean.playlistmaker.settings.data.impl.SettingsRepositoryImpl
import com.iclean.playlistmaker.settings.domain.SettingsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val settingsRepositoryModule = module {
    single{
        androidContext().getSharedPreferences("settings_app", Context.MODE_PRIVATE)
    }
    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }
}
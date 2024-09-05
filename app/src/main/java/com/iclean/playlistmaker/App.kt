package com.iclean.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.iclean.playlistmaker.player.di.playerInteractorModule
import com.iclean.playlistmaker.player.di.playerRepositoryModule
import com.iclean.playlistmaker.player.di.playerViewModel
import com.iclean.playlistmaker.search.di.searchDataModule
import com.iclean.playlistmaker.search.di.searchInteractorModule
import com.iclean.playlistmaker.search.di.searchRepositoryModule
import com.iclean.playlistmaker.search.di.searchViewModelModule
import com.iclean.playlistmaker.settings.data.impl.SettingsRepositoryImpl
import com.iclean.playlistmaker.settings.di.settingInteractorModule
import com.iclean.playlistmaker.settings.di.settingsRepositoryModule
import com.iclean.playlistmaker.settings.di.settingsViewModelModule
import com.iclean.playlistmaker.sharing.di.sharingInteractorModule
import com.iclean.playlistmaker.sharing.di.sharingRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    var darkTheme : Boolean = false

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(searchDataModule, searchRepositoryModule, searchInteractorModule, searchViewModelModule,
                playerRepositoryModule, playerInteractorModule, playerViewModel,
                sharingInteractorModule, sharingRepositoryModule,
                settingInteractorModule, settingsRepositoryModule, settingsViewModelModule)
        }

        val theme = getSharedPreferences(THEME_NAME, MODE_PRIVATE)
        val themeRepository = SettingsRepositoryImpl(theme)

        switchTheme(themeRepository.switchTheme(this).darkTheme)
    }



        //Меняем тему приложения
        fun switchTheme(darkThemeEnabled: Boolean) {
            darkTheme = darkThemeEnabled
            AppCompatDelegate.setDefaultNightMode(
                if (darkThemeEnabled) {
                    AppCompatDelegate.MODE_NIGHT_YES

                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }

            )
        }

    companion object {
        private const val THEME_NAME = "settings_app"
    }

    }


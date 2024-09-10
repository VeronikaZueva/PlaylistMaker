package com.iclean.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.iclean.playlistmaker.di.playerModule
import com.iclean.playlistmaker.di.searchModule
import com.iclean.playlistmaker.di.settingsModule
import com.iclean.playlistmaker.di.sharingModule
import com.iclean.playlistmaker.settings.domain.SettingsInteractor
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    private var darkTheme : Boolean = false

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(playerModule, settingsModule, sharingModule)
        }
        val themeInteractor by inject<SettingsInteractor>()
        val darkTheme = themeInteractor.switchTheme().darkTheme
        switchTheme(darkTheme)

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



    }


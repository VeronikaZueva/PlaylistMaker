package com.iclean.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.iclean.playlistmaker.general.Creator


class App : Application() {

    private var darkTheme : Boolean = false

    override fun onCreate() {
        super.onCreate()
        Creator.app = this
        val themeSwitcher = Creator.getSettingsInteractor()
        darkTheme = themeSwitcher.switchTheme().darkTheme
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


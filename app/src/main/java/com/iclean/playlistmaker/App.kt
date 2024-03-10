package com.iclean.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {

    companion object {
        const val SETTING_PARAMS = "SETTING_PARAMS"
        const val IS_DARK_THEME = false
        const val KEY_FOR_THEME = "KEY_THEME"
    }

    private var darkTheme = IS_DARK_THEME

    override fun onCreate() {
        super.onCreate()
        //Достаем сохраненные настроки ShredPreferences
        val sharedPreferences = getSharedPreferences(SETTING_PARAMS, MODE_PRIVATE)
        darkTheme = sharedPreferences.getBoolean(KEY_FOR_THEME, IS_DARK_THEME)
        switchTheme(darkTheme)
    }
        //Меняем тему приложения
    fun switchTheme(darkThemeEnabled : Boolean) {
        darkTheme = darkThemeEnabled
            AppCompatDelegate.setDefaultNightMode(
                if(darkThemeEnabled) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )

    }

}
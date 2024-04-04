package com.iclean.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {

companion object {
    const val SETTING_PARAMS = "settings_app"
    const val KEY_FOR_THEME = "key_theme"
    const val THEME_DEFAULT = false
}

    private var darkTheme = THEME_DEFAULT

    override fun onCreate() {
        super.onCreate()
        //Достаем сохраненные настроки ShredPreferences
        val sharedPreferences = getSharedPreferences(SETTING_PARAMS, MODE_PRIVATE)
        darkTheme = sharedPreferences.getBoolean(KEY_FOR_THEME, darkTheme)
        switchTheme(darkTheme)
    }

        //Меняем тему приложения
     fun switchTheme(darkThemeEnabled : Boolean) {
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
package com.iclean.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate


class App : Application() {

companion object {
    const val SETTING_PARAMS = "settings_app"
    const val KEY_FOR_THEME = "key_theme"
    const val HISTORY_KEY = "history_key"
    const val THEME_DEFAULT = false
    lateinit var sharedPreferences : SharedPreferences
}

    private var darkTheme = THEME_DEFAULT


    override fun onCreate() {
        super.onCreate()
        //Достаем сохраненные настроки ShredPreferences
        sharedPreferences = getSharedPreferences(SETTING_PARAMS, MODE_PRIVATE)
        darkTheme = sharedPreferences.getBoolean(KEY_FOR_THEME, darkTheme)
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
            setValueThemeInShared(darkThemeEnabled)
        }

    //Записываем изменение темы в SharedPreferences
    private fun setValueThemeInShared(darkTheme : Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_FOR_THEME, darkTheme)
            .apply()
    }



    }


package com.iclean.playlistmaker.settings.data.impl

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import com.iclean.playlistmaker.settings.data.SettingsRepository
import com.iclean.playlistmaker.settings.domain.model.ThemeSetting

class SettingsRepositoryImpl(private val context : Context) : SettingsRepository {
    //Упрощаем класс App
    private val sharedPref: SharedPreferences = context.getSharedPreferences(
        SETTING_PARAMS, Context.MODE_PRIVATE
    )
    override fun switchTheme() : ThemeSetting {
        return ThemeSetting(sharedPref.getBoolean(
            KEY_FOR_THEME,
            context.applicationContext.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        ))
    }

    override fun updateThemeInApp(darkThemeSetting: ThemeSetting) {
        sharedPref.edit().putBoolean(KEY_FOR_THEME, darkThemeSetting.darkTheme).apply()
    }

    companion object {
        const val SETTING_PARAMS = "settings_app"
        const val KEY_FOR_THEME = "key_theme"
    }
}
package com.iclean.playlistmaker.settings.data.impl

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import com.iclean.playlistmaker.App
import com.iclean.playlistmaker.settings.domain.SettingsRepository
import com.iclean.playlistmaker.settings.domain.model.ThemeSetting

class SettingsRepositoryImpl(private val context : Context, application : Application) : SettingsRepository {

    private val sharedPref : SharedPreferences = context.getSharedPreferences(SETTING_PARAMS, Context.MODE_PRIVATE)
    private val app by lazy {application as App }
    override fun getTheme() : ThemeSetting {
        return ThemeSetting(sharedPref.getBoolean(
            KEY_FOR_THEME,
            context.applicationContext.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES))
    }

    override fun switchTheme(darkTheme : Boolean) {
        app.switchTheme(darkTheme)
    }

    override fun updateThemeInApp(darkThemeSetting: ThemeSetting) {
        sharedPref.edit().putBoolean(KEY_FOR_THEME, darkThemeSetting.darkTheme).apply()
    }

    companion object {
        const val SETTING_PARAMS = "settings_app"
        const val KEY_FOR_THEME = "key_theme"
    }
}
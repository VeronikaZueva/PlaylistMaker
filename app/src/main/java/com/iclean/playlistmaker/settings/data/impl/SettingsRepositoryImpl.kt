package com.iclean.playlistmaker.settings.data.impl

import android.app.Application
import android.content.SharedPreferences
import android.content.res.Configuration
import com.iclean.playlistmaker.settings.domain.SettingsRepository
import com.iclean.playlistmaker.settings.domain.model.ThemeSetting

class SettingsRepositoryImpl(private val sharedPref : SharedPreferences) : SettingsRepository {

    private var darkTheme : Boolean = false

    override fun switchTheme(app : Application) : ThemeSetting {
        darkTheme = when(app.applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_YES) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }

        return ThemeSetting(sharedPref.getBoolean(SETTING_PARAMS, darkTheme))
    }

    override fun updateThemeInApp(darkThemeSetting: ThemeSetting) {
        sharedPref.edit().putBoolean(SETTING_PARAMS, darkThemeSetting.darkTheme).apply()
    }

    companion object {
        const val SETTING_PARAMS = "settings_app"
    }
}
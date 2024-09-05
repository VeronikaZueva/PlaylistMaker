package com.iclean.playlistmaker.settings.domain

import android.app.Application
import com.iclean.playlistmaker.settings.domain.model.ThemeSetting


interface SettingsRepository {
    fun switchTheme(app : Application) : ThemeSetting
    fun updateThemeInApp (darkThemeSetting : ThemeSetting)
}
package com.iclean.playlistmaker.settings.data

import com.iclean.playlistmaker.settings.domain.model.ThemeSetting


interface SettingsRepository {
    fun switchTheme() : ThemeSetting
    fun updateThemeInApp (darkThemeSetting : ThemeSetting)
}
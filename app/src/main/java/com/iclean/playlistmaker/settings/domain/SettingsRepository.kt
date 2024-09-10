package com.iclean.playlistmaker.settings.domain


import com.iclean.playlistmaker.settings.domain.model.ThemeSetting


interface SettingsRepository {
    fun switchTheme() : ThemeSetting
    fun updateThemeInApp (darkThemeSetting : ThemeSetting)
}
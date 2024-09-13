package com.iclean.playlistmaker.settings.domain

import com.iclean.playlistmaker.settings.domain.model.ThemeSetting

interface SettingsInteractor {
    fun getTheme() : ThemeSetting
    fun switchTheme(darkTheme : Boolean)
    fun updateThemeInApp(darkThemeSetting: ThemeSetting)
}
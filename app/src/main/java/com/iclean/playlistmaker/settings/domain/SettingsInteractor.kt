package com.iclean.playlistmaker.settings.domain

import com.iclean.playlistmaker.settings.domain.model.ThemeSetting

interface SettingsInteractor {
    fun switchTheme() : ThemeSetting
    fun updateThemeInApp(darkThemeSetting: ThemeSetting)
}
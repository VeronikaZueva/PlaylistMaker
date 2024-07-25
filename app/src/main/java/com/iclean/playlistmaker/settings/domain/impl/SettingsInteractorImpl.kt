package com.iclean.playlistmaker.settings.domain.impl


import com.iclean.playlistmaker.settings.data.SettingsRepository
import com.iclean.playlistmaker.settings.domain.SettingsInteractor
import com.iclean.playlistmaker.settings.domain.model.ThemeSetting


class SettingsInteractorImpl(private val repository: SettingsRepository) : SettingsInteractor  {
    override fun switchTheme() : ThemeSetting {return repository.switchTheme()}
    override fun updateThemeInApp(darkThemeSetting: ThemeSetting) {repository.updateThemeInApp(darkThemeSetting)}
}
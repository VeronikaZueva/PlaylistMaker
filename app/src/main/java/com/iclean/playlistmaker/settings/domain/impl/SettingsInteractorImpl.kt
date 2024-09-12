package com.iclean.playlistmaker.settings.domain.impl


import com.iclean.playlistmaker.settings.domain.SettingsRepository
import com.iclean.playlistmaker.settings.domain.SettingsInteractor
import com.iclean.playlistmaker.settings.domain.model.ThemeSetting


class SettingsInteractorImpl(private val repository: SettingsRepository) : SettingsInteractor  {
    override fun getTheme() : ThemeSetting {return repository.getTheme()}
    override fun switchTheme(darkTheme : Boolean) {
        repository.switchTheme(darkTheme)
    }
    override fun updateThemeInApp(darkThemeSetting: ThemeSetting) {repository.updateThemeInApp(darkThemeSetting)}
}
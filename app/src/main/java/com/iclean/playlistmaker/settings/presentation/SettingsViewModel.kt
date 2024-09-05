package com.iclean.playlistmaker.settings.presentation

import androidx.lifecycle.ViewModel
import com.iclean.playlistmaker.settings.domain.SettingsInteractor
import com.iclean.playlistmaker.settings.domain.model.ThemeSetting
import com.iclean.playlistmaker.sharing.domain.SharingInteractor

class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor,
    private val sharingInteractor : SharingInteractor
    ) : ViewModel() {

    fun switchTheme(isChecked : Boolean) {settingsInteractor.updateThemeInApp(ThemeSetting(isChecked))}
    fun shareApp() {sharingInteractor.shareApp()}
    fun sendMessage() {sharingInteractor.sendMessage()}
    fun goToLink() {sharingInteractor.goToLink()}

}
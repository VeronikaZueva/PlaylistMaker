package com.iclean.playlistmaker.settings.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iclean.playlistmaker.settings.domain.SettingsInteractor
import com.iclean.playlistmaker.settings.domain.model.ThemeSetting
import com.iclean.playlistmaker.sharing.domain.SharingInteractor

class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor,
    private val sharingInteractor : SharingInteractor
    ) : ViewModel() {

    //Задаем LiveData, чтобы отслеживать изменения
    private val themeLiveData = MutableLiveData<ThemeSetting>()

    init {
        themeLiveData.value = settingsInteractor.getTheme()
    }

    fun switchTheme(isChecked : Boolean) {
        settingsInteractor.updateThemeInApp(ThemeSetting(isChecked))
        themeLiveData.value = settingsInteractor.getTheme()
        themeLiveData.value?.let { settingsInteractor.switchTheme(it.darkTheme) }
    }
    fun shareApp() {sharingInteractor.shareApp()}
    fun sendMessage() {sharingInteractor.sendMessage()}
    fun goToLink() {sharingInteractor.goToLink()}

    fun getLiveData() : LiveData<ThemeSetting> = themeLiveData

}
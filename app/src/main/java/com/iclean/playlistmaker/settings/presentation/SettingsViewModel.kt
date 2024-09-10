package com.iclean.playlistmaker.settings.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iclean.playlistmaker.App
import com.iclean.playlistmaker.settings.domain.SettingsInteractor
import com.iclean.playlistmaker.settings.domain.model.ThemeSetting
import com.iclean.playlistmaker.sharing.domain.SharingInteractor

class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor,
    private val sharingInteractor : SharingInteractor,
    application : Application
    ) : ViewModel() {

    //Переопределяем уровень приложения и задаем LiveData, чтобы отслеживать изменения
    private val app by lazy {application as App}
    private val themeLiveData = MutableLiveData<ThemeSetting>()

    init {
        themeLiveData.value = settingsInteractor.switchTheme()
    }

    fun switchTheme(isChecked : Boolean) {
        settingsInteractor.updateThemeInApp(ThemeSetting(isChecked))
        themeLiveData.value = settingsInteractor.switchTheme()
        themeLiveData.value?.let {app.switchTheme(it.darkTheme)}
    }
    fun shareApp() {sharingInteractor.shareApp()}
    fun sendMessage() {sharingInteractor.sendMessage()}
    fun goToLink() {sharingInteractor.goToLink()}

    fun getLiveData() : LiveData<ThemeSetting> = themeLiveData

}
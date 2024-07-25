package com.iclean.playlistmaker.settings.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iclean.playlistmaker.App
import com.iclean.playlistmaker.general.Creator
import com.iclean.playlistmaker.settings.domain.SettingsInteractor
import com.iclean.playlistmaker.settings.domain.model.ThemeSetting
import com.iclean.playlistmaker.sharing.domain.SharingInteractor

class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor,
    private val sharingInteractor : SharingInteractor,
    app: Application
    ) : ViewModel() {
    private val app by lazy {app as App }
    private val themeLiveData = MutableLiveData<ThemeSetting>()

    init {
        themeLiveData.value = settingsInteractor.switchTheme()
    }
    fun switchThemeLiveData() : LiveData<ThemeSetting> = themeLiveData

    fun switchTheme(darkTheme : Boolean) {
        if(darkTheme != themeLiveData.value?.darkTheme) {
            settingsInteractor.updateThemeInApp(ThemeSetting(darkTheme))
            themeLiveData.value = settingsInteractor.switchTheme()
            themeLiveData.value.let {
                if (it != null) {
                    app.switchTheme(it.darkTheme)
                }
            }
        }
    }

    fun shareApp() {sharingInteractor.shareApp()}
    fun sendMessage() {sharingInteractor.sendMessage()}
    fun goToLink() {sharingInteractor.goToLink()}
    companion object {
        fun getViewModelFactory(context : Context) : ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>) : T {
                    return SettingsViewModel(Creator.getSettingsInteractor(), Creator.getSharingInteractor(context), Creator.app) as T
                }
            }
    }
}
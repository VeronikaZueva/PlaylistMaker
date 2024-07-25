package com.iclean.playlistmaker.general

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.iclean.playlistmaker.player.data.repository.PlayerRepositoryImpl
import com.iclean.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.iclean.playlistmaker.player.domain.PlayerInteractor
import com.iclean.playlistmaker.player.data.PlayerRepository
import com.iclean.playlistmaker.search.data.SearchRepository
import com.iclean.playlistmaker.search.data.impl.SearchRepositoryImpl
import com.iclean.playlistmaker.search.data.network.RetrofitImpl
import com.iclean.playlistmaker.search.domain.SearchInteractor
import com.iclean.playlistmaker.search.domain.impl.SearchInteractorImpl
import com.iclean.playlistmaker.settings.data.SettingsRepository
import com.iclean.playlistmaker.settings.data.impl.SettingsRepositoryImpl
import com.iclean.playlistmaker.settings.domain.SettingsInteractor
import com.iclean.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.iclean.playlistmaker.sharing.data.SharingRepository
import com.iclean.playlistmaker.sharing.data.impl.SharingRepositoryImpl
import com.iclean.playlistmaker.sharing.domain.SharingInteractor
import com.iclean.playlistmaker.sharing.domain.impl.SharingInteractorImpl

object Creator {
    lateinit var app : Application

    //Для Настроек
    private fun getSettingsRepository() : SettingsRepository {
        return SettingsRepositoryImpl(app)
    }
    fun getSettingsInteractor() : SettingsInteractor {
        return SettingsInteractorImpl(getSettingsRepository())
    }
    private fun getSharingRepository(context : Context) : SharingRepository {
        return SharingRepositoryImpl(context)
    }
    fun getSharingInteractor(context : Context) : SharingInteractor {
        return SharingInteractorImpl(getSharingRepository(context))
    }
    //Для Медиаплеера
    private fun getPlayerRepository(url: String?, runnable: Runnable) : PlayerRepository {
        return PlayerRepositoryImpl(url!!, runnable)
    }
    fun getPlayerInteractor(url: String?, runnable: Runnable) : PlayerInteractor {
        return PlayerInteractorImpl(getPlayerRepository(url, runnable))
    }

    //Для поиска
    private fun getSearchRepository(sharedPreferences: SharedPreferences, context: Context) : SearchRepository {
        return SearchRepositoryImpl(sharedPreferences, RetrofitImpl(context))
    }
    fun getSearchInteractor(sharedPreferences: SharedPreferences, context: Context) : SearchInteractor {
        return SearchInteractorImpl(getSearchRepository(sharedPreferences, context))
    }

}
package com.iclean.playlistmaker.di

import com.iclean.playlistmaker.sharing.data.impl.SharingRepositoryImpl
import com.iclean.playlistmaker.sharing.domain.SharingInteractor
import com.iclean.playlistmaker.sharing.domain.SharingRepository
import com.iclean.playlistmaker.sharing.domain.impl.SharingInteractorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharingModule = module {
    single<SharingRepository> {
        SharingRepositoryImpl(context = androidContext())
    }
    single<SharingInteractor> {
        SharingInteractorImpl(repository = get(), app = androidContext())
    }
}
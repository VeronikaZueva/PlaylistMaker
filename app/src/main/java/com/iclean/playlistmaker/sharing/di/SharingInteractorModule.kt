package com.iclean.playlistmaker.sharing.di

import com.iclean.playlistmaker.sharing.domain.SharingInteractor
import com.iclean.playlistmaker.sharing.domain.impl.SharingInteractorImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharingInteractorModule = module {
    single<SharingInteractor> {
        SharingInteractorImpl(get(), androidContext())
    }
}
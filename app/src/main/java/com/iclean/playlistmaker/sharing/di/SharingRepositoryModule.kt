package com.iclean.playlistmaker.sharing.di

import com.iclean.playlistmaker.sharing.data.impl.SharingRepositoryImpl
import com.iclean.playlistmaker.sharing.domain.SharingRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val sharingRepositoryModule = module {
    single<SharingRepository> {
        SharingRepositoryImpl(androidContext())
    }
}
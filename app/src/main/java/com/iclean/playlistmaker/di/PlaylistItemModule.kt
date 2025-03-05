package com.iclean.playlistmaker.di

import androidx.room.Room
import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.db.convertor.PlaylistDbConvertor
import com.iclean.playlistmaker.playlist.data.PlaylistItemRepositoryImpl
import com.iclean.playlistmaker.playlist.domain.PlaylistItemInteractor
import com.iclean.playlistmaker.playlist.domain.PlaylistItemRepository
import com.iclean.playlistmaker.playlist.domain.impl.PlaylistItemInteractorImpl
import com.iclean.playlistmaker.playlist.presentation.PlaylistItemViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playlistItemModule = module {
    viewModel {
        PlaylistItemViewModel(playlistItemInteractor = get(), playlistInteractor = get())
    }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    factory{
        PlaylistDbConvertor()
    }
    single<PlaylistItemRepository> {
        PlaylistItemRepositoryImpl(appDataBase = get(), dbConvertor = get())
    }
    single<PlaylistItemInteractor> {
        PlaylistItemInteractorImpl(playlistItemRepository = get(), get())
    }
}
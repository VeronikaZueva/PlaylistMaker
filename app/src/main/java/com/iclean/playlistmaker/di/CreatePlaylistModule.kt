package com.iclean.playlistmaker.di

import androidx.room.Room
import com.iclean.playlistmaker.create.domain.CreatePlaylistRepository
import com.iclean.playlistmaker.create.data.repository.CreatePlaylistRepositoryImpl
import com.iclean.playlistmaker.create.domain.CreatePlaylistInteractor
import com.iclean.playlistmaker.create.domain.impl.CreatePlaylistInteractorImpl
import com.iclean.playlistmaker.create.presentation.CreatePlaylistViewModel
import com.iclean.playlistmaker.create.presentation.EditPlaylistViewModel
import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.db.convertor.PlaylistDbConvertor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val createPlaylistModule = module {
        viewModel<CreatePlaylistViewModel> {
            CreatePlaylistViewModel(createPlaylistInteractor = get())
        }
        viewModel<EditPlaylistViewModel> {
             EditPlaylistViewModel(createPlaylistInteractor = get(), playlistItemInteractor = get(), playlistInteractor = get())
        }
        factory<CreatePlaylistInteractor> {
            CreatePlaylistInteractorImpl(createPlaylistRepository = get())
        }
        single<CreatePlaylistRepository> {
            CreatePlaylistRepositoryImpl(appDataBase = get(), dbConvertor = get(), context = androidContext())
        }
        single {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
        }
        factory{
            PlaylistDbConvertor()
        }
    }


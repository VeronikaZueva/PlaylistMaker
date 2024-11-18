package com.iclean.playlistmaker.di

import androidx.room.Room
import com.iclean.playlistmaker.media.data.MediaRepositoryImpl
import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.db.convertor.TrackDbConvertor
import com.iclean.playlistmaker.media.domain.MediaInteractor
import com.iclean.playlistmaker.media.domain.MediaRepository
import com.iclean.playlistmaker.media.domain.impl.MediaInteractorImpl
import com.iclean.playlistmaker.media.presentation.FavoriteFragmentViewModel
import com.iclean.playlistmaker.media.presentation.PlaylistFragmentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mediaModule = module {
    viewModel {
        FavoriteFragmentViewModel(favoriteInteractor = get())
    }
    viewModel {
        PlaylistFragmentViewModel()
    }
    single<MediaRepository> {
        MediaRepositoryImpl(appDatabase = get(), trackDbConvertor = get())
    }
    single<MediaInteractor> {
        MediaInteractorImpl(mediaRepository = get())
    }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .build()
    }
    factory { TrackDbConvertor() }
}
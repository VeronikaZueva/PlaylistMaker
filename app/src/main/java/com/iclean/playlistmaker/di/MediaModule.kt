package com.iclean.playlistmaker.di

import androidx.room.Room
import com.iclean.playlistmaker.media.data.favorite.MediaRepositoryImpl
import com.iclean.playlistmaker.db.AppDatabase
import com.iclean.playlistmaker.db.convertor.TrackDbConvertor
import com.iclean.playlistmaker.media.data.playlists.PlaylistRepositoryImpl
import com.iclean.playlistmaker.media.domain.favorite.MediaInteractor
import com.iclean.playlistmaker.media.domain.favorite.MediaRepository
import com.iclean.playlistmaker.media.domain.favorite.impl.MediaInteractorImpl
import com.iclean.playlistmaker.media.domain.playlists.PlaylistInteractor
import com.iclean.playlistmaker.media.domain.playlists.PlaylistRepository
import com.iclean.playlistmaker.media.domain.playlists.impl.PlaylistInteractorImpl
import com.iclean.playlistmaker.media.presentation.favorite.FavoriteFragmentViewModel
import com.iclean.playlistmaker.media.presentation.playlists.PlaylistFragmentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mediaModule = module {
    //Для экрана Избранное
    viewModel {
        FavoriteFragmentViewModel(favoriteInteractor = get())
    }
    single<MediaRepository> {
        MediaRepositoryImpl(appDatabase = get(), trackDbConvertor = get())
    }
    single<MediaInteractor> {
        MediaInteractorImpl(mediaRepository = get())
    }

    //Для экрана Плейлисты
    viewModel {
        PlaylistFragmentViewModel(playlistInteractor = get())
    }
    single<PlaylistRepository> {
        PlaylistRepositoryImpl(appDataBase = get(), dbConvertor = get())
    }
    single<PlaylistInteractor> {
        PlaylistInteractorImpl(playlistRepository = get())
    }
    //База данных
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    factory { TrackDbConvertor() }

}
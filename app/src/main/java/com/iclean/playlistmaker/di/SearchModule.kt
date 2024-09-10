package com.iclean.playlistmaker.di

import android.content.Context
import com.google.gson.Gson
import com.iclean.playlistmaker.search.data.NetworkClient
import com.iclean.playlistmaker.search.data.dto.History
import com.iclean.playlistmaker.search.data.impl.SearchHistoryImpl
import com.iclean.playlistmaker.search.data.impl.SearchRepositoryImpl
import com.iclean.playlistmaker.search.data.network.ITunesApi
import com.iclean.playlistmaker.search.data.network.RetrofitImpl
import com.iclean.playlistmaker.search.domain.HistoryInt
import com.iclean.playlistmaker.search.domain.SearchHistoryInt
import com.iclean.playlistmaker.search.domain.SearchInteractor
import com.iclean.playlistmaker.search.domain.SearchRepository
import com.iclean.playlistmaker.search.domain.impl.HistoryInteractorImpl
import com.iclean.playlistmaker.search.domain.impl.SearchInteractorImpl
import com.iclean.playlistmaker.search.presentation.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val searchModule = module {
    //Внешние библиотеки:
//Для RetrofitImpl
    single<ITunesApi> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ITunesApi::class.java)
    }
    single {
        androidContext().getSharedPreferences("SearchHistoryInt", Context.MODE_PRIVATE)
    }
    factory {
        Gson()
    }
    //Главные классы dto
    single<HistoryInt> {
        History(sharePref = get())
    }
    single<NetworkClient> {
        RetrofitImpl(iTunesApi = get(), context = androidContext())
    }
    single<SearchRepository> {
        SearchRepositoryImpl(networkClient = get())
    }
    single<SearchHistoryInt> {
        SearchHistoryImpl(history = get())
    }
    factory<SearchInteractor> {
        SearchInteractorImpl(repository = get())
    }
    factory<SearchHistoryInt>  {
        HistoryInteractorImpl(historyRepository = get())
    }
    viewModel {
        SearchViewModel(searchInteractor = get(), historyInteractor = get())
    }
}

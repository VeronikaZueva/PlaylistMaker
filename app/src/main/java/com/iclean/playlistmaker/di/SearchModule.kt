package com.iclean.playlistmaker.di

import com.google.gson.Gson
import com.iclean.playlistmaker.search.data.NetworkClient
import com.iclean.playlistmaker.search.data.dto.History
import com.iclean.playlistmaker.search.data.impl.SearchHistoryImpl
import com.iclean.playlistmaker.search.data.impl.SearchRepositoryImpl
import com.iclean.playlistmaker.search.data.network.ITunesApi
import com.iclean.playlistmaker.search.data.network.RetrofitImpl
import com.iclean.playlistmaker.search.domain.HistoryInt
import com.iclean.playlistmaker.search.domain.SearchHistoryInt
import com.iclean.playlistmaker.search.domain.SearchHistoryInteractor
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
    //СЛОЙ DATA
    //Repository
    single<SearchRepository> {
        SearchRepositoryImpl(networkClient = get())
    }
    single<NetworkClient> {
        RetrofitImpl(iTunesApi = get(), context = androidContext())
    }
    single<ITunesApi> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ITunesApi::class.java)
    }
    factory {
        Gson()
    }
    //SearchHistoryRepository
    single<SearchHistoryInt> {
        SearchHistoryImpl(history = get())
    }
    single<HistoryInt> {
        History(context = get())
    }


    //СЛОЙ DOMAIN
    factory<SearchInteractor> {
        SearchInteractorImpl(repository = get())
    }
    factory<SearchHistoryInteractor>  {
        HistoryInteractorImpl(historyRepository = get())
    }
    viewModel {
        SearchViewModel(searchInteractor = get(), historyInteractor = get())
    }
}

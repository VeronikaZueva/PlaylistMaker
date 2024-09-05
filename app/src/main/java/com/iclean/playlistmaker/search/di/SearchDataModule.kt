package com.iclean.playlistmaker.search.di


import android.content.Context
import com.google.gson.Gson
import com.iclean.playlistmaker.search.data.NetworkClient
import com.iclean.playlistmaker.search.data.dto.History
import com.iclean.playlistmaker.search.data.network.ITunesApi
import com.iclean.playlistmaker.search.data.network.RetrofitImpl
import com.iclean.playlistmaker.search.domain.HistoryInt
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val searchDataModule = module {
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
        History(get())
    }
    single<NetworkClient> {
        RetrofitImpl(get(), androidContext())
    }
}


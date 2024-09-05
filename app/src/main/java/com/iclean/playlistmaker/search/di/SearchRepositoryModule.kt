package com.iclean.playlistmaker.search.di

import com.iclean.playlistmaker.search.data.impl.SearchHistoryImpl
import com.iclean.playlistmaker.search.data.impl.SearchRepositoryImpl
import com.iclean.playlistmaker.search.domain.SearchHistoryInt
import com.iclean.playlistmaker.search.domain.SearchRepository
import org.koin.dsl.module


val searchRepositoryModule = module {
        single<SearchRepository> {
            SearchRepositoryImpl(get())
        }
        single<SearchHistoryInt> {
            SearchHistoryImpl(get())
        }


    }

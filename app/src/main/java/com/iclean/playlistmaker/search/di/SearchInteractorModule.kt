package com.iclean.playlistmaker.search.di


import com.iclean.playlistmaker.search.domain.SearchHistoryInt
import com.iclean.playlistmaker.search.domain.SearchInteractor
import com.iclean.playlistmaker.search.domain.impl.HistoryInteractorImpl
import com.iclean.playlistmaker.search.domain.impl.SearchInteractorImpl
import org.koin.dsl.module


    val searchInteractorModule = module {
        single<SearchInteractor> {
            SearchInteractorImpl(get())
        }
        single<SearchHistoryInt>  {
            HistoryInteractorImpl(get())
        }
    }

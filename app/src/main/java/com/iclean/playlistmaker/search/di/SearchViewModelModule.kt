package com.iclean.playlistmaker.search.di

import com.iclean.playlistmaker.search.presentation.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

    val searchViewModelModule = module {
        viewModel {
            SearchViewModel(get(), get())
        }
    }

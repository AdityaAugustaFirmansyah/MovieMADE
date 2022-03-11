package com.aditya.favourite

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FavouriteModule {
    val favouriteModule = module {
        viewModel { MovieFavouriteViewModel(get()) }
    }
}
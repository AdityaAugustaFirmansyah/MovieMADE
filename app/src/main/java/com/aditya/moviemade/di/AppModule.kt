package com.aditya.moviemade.di

import com.aditya.moviemade.ui.detail.DetailMovieViewModel
import com.aditya.moviemade.ui.movie.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val useCaseModule = module {
        factory<com.aditya.core.domain.usecase.MovieUseCase> {
            com.aditya.core.domain.usecase.MovieInteractor(
                get()
            )
        }
    }

    val viewModelModule = module {
        viewModel { MovieViewModel(get()) }
        viewModel { DetailMovieViewModel(get()) }
    }
}
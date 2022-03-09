package com.aditya.favourite.moviefavourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class MovieFavouriteViewModel(private val useCase: com.aditya.core.domain.usecase.MovieUseCase) :ViewModel() {

    fun getData():LiveData<List<com.aditya.core.domain.model.Movie>>{
        return useCase.getAllMovieFavourite().asLiveData()
    }
}
package com.aditya.moviemade.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.core.domain.model.Movie
import com.aditya.core.domain.usecase.MovieUseCase
import com.aditya.core.vo.Resource

class MovieViewModel(private val useCase: MovieUseCase) : ViewModel() {
    fun getData(sort:String): LiveData<Resource<List<Movie>>> {
        return useCase.getAllMovie(sort).asLiveData()
    }
}
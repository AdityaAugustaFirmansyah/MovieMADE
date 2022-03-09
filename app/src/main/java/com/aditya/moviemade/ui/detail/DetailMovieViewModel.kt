package com.aditya.moviemade.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.core.domain.model.Movie
import com.aditya.core.domain.usecase.MovieUseCase
import com.aditya.core.vo.Resource

class DetailMovieViewModel(private val useCase: MovieUseCase):ViewModel() {

    fun detailMovieLiveData(id:Int):LiveData<Resource<Movie>>{
        return useCase.getMovieById(id.toString()).asLiveData()
    }

    fun setFavourite(movieEntity: Movie){
        movieEntity.favourite = !movieEntity.favourite
        useCase.setFavourite(movieEntity,movieEntity.favourite)
    }
}
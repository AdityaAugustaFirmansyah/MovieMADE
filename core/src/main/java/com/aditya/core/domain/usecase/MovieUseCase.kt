package com.aditya.core.domain.usecase

import com.aditya.core.domain.model.Movie
import com.aditya.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovie(sort:String):Flow<Resource<List<Movie>>>
    fun getAllMovieFavourite():Flow<List<Movie>>
    fun getMovieById(id:String):Flow<Resource<Movie>>
    suspend fun setFavourite(movie: Movie, state:Boolean)
}
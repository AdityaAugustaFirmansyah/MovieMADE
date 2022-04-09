package com.aditya.core.domain.repository

import com.aditya.core.domain.model.Movie
import com.aditya.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getAllMovie(sort:String):Flow<Resource<List<Movie>>>
    fun getMovieById(id:String):Flow<Resource<Movie>>
    fun getAllMovieFavourite():Flow<List<Movie>>
    suspend fun setFavouriteMovie(movie: Movie, state:Boolean)
}
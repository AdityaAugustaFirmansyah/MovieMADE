package com.aditya.core.data

import androidx.lifecycle.LiveData
import com.aditya.core.data.source.local.entity.MovieEntity
import com.aditya.core.vo.Resource

interface MovieDataSource {
    fun getAllMovie(sort:String):LiveData<Resource<List<MovieEntity>>>
    fun getMovieById(id:String):LiveData<Resource<MovieEntity>>
    fun updateMovie(movieEntity: MovieEntity)
    fun getAllMovieFavourite(): LiveData<List<MovieEntity>>
}
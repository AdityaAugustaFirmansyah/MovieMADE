package com.aditya.core.data.source.local

import com.aditya.core.data.source.local.entity.MovieEntity
import com.aditya.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource (private val movieDao: MovieDao) {

    fun getAllMovie():Flow<List<MovieEntity>> = movieDao.getAllMovies()
    fun getAllMovieAsc():Flow<List<MovieEntity>> = movieDao.getAllMoviesAsc()
    fun getAllMovieDesc():Flow<List<MovieEntity>> = movieDao.getAllMoviesDesc()
    suspend fun insertMovies(movieList: List<MovieEntity>) = movieDao.insertMovies(movieList)
    fun getAllMovieFavourite(): Flow<List<MovieEntity>> = movieDao.getAllMoviesFavourite()
    fun updateMovie(movieEntity: MovieEntity) = movieDao.updateMovie(movieEntity)
    fun getMovie(id: Int): Flow<MovieEntity> = movieDao.getMovie(id)
}
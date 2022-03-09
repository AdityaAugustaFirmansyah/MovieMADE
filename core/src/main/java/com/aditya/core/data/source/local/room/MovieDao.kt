package com.aditya.core.data.source.local.room

import androidx.room.*
import com.aditya.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieEntity")
    fun getAllMovies():Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity ORDER BY title ASC")
    fun getAllMoviesAsc():Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity ORDER BY title DESC")
    fun getAllMoviesDesc():Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movieList: List<MovieEntity>)

    @Update
    fun updateMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    fun getMovie(id:Int):Flow<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE favourite = 1")
    fun getAllMoviesFavourite():Flow<List<MovieEntity>>
}
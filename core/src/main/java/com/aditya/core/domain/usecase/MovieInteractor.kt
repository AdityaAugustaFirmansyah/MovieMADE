package com.aditya.core.domain.usecase

import com.aditya.core.domain.model.Movie
import com.aditya.core.domain.repository.IMovieRepository
import com.aditya.core.vo.Resource
import kotlinx.coroutines.flow.Flow

class MovieInteractor (private val movieRepository: IMovieRepository): MovieUseCase {
    override fun getAllMovie(sort:String): Flow<Resource<List<Movie>>>
    = movieRepository.getAllMovie(sort)

    override fun getAllMovieFavourite(): Flow<List<Movie>>
    = movieRepository.getAllMovieFavourite()

    override fun getMovieById(id: String): Flow<Resource<Movie>>
    = movieRepository.getMovieById(id)

    override fun setFavourite(movie: Movie, state: Boolean)
    = movieRepository.setFavouriteMovie(movie,state)
}
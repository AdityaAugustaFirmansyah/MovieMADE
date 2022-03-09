package com.aditya.core.data

import com.aditya.core.data.source.local.LocalDataSource
import com.aditya.core.data.source.local.entity.MovieEntity
import com.aditya.core.data.source.remote.ApiResponse
import com.aditya.core.data.source.remote.RemoteDataSource
import com.aditya.core.data.source.remote.response.DetailMovieResponse
import com.aditya.core.data.source.remote.response.MovieResponse
import com.aditya.core.domain.model.Movie
import com.aditya.core.domain.repository.IMovieRepository
import com.aditya.core.utils.AppExecutors
import com.aditya.core.utils.SortUtils
import com.aditya.core.utils.convertMovieEntityToDomainModel
import com.aditya.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovie(sort: String): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, MovieResponse>(appExecutors) {
            override fun loadFromDb(): Flow<List<Movie>> {
                return when (sort) {
                    SortUtils.DEFAULT -> localDataSource.getAllMovie()
                    SortUtils.ASCENDING -> localDataSource.getAllMovieAsc()
                    SortUtils.DESCENDING -> localDataSource.getAllMovieDesc()
                    else -> localDataSource.getAllMovie()
                }.map { it.convertMovieEntityToDomainModel() }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): Flow<ApiResponse<MovieResponse>> {
                return remoteDataSource.getAllMovie()
            }

            override fun saveCallResult(data: MovieResponse) {
                localDataSource.insertMovies(data.results.map {
                    MovieEntity(
                        it.posterPath,
                        it.backdropPath,
                        it.overview,
                        it.releaseDate,
                        it.id,
                        it.originalTitle,
                        it.title,
                        it.originalLanguage,
                        it.popularity,
                        false,
                        it.voteCount,
                        it.adult,
                        it.voteAverage
                    )
                })
            }

        }.asFlow()
    }

    override fun getMovieById(id: String): Flow<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, DetailMovieResponse>(appExecutors) {
            override fun loadFromDb(): Flow<Movie> {
                return localDataSource.getMovie(id.toInt()).map {
                    Movie(
                        it.posterPath,
                        it.backdropPath,
                        it.overview,
                        it.releaseDate,
                        it.id,
                        it.originalTitle,
                        it.title,
                        it.originalLanguage,
                        it.popularity,
                        it.favourite,
                        it.voteCount,
                        it.adult,
                        it.voteAverage
                    )
                }
            }

            override fun shouldFetch(data: Movie?): Boolean {
                return data == null
            }

            override fun createCall(): Flow<ApiResponse<DetailMovieResponse>> {
                return remoteDataSource.getMovieById(id)
            }

            override fun saveCallResult(data: DetailMovieResponse) {

            }

        }.asFlow()
    }

    override fun getAllMovieFavourite(): Flow<List<Movie>> {
        return localDataSource.getAllMovieFavourite().map { it.convertMovieEntityToDomainModel() }
    }

    override fun setFavouriteMovie(movie: Movie, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.updateMovie(
                MovieEntity(
                    movie.posterPath,
                    movie.backdropPath,
                    movie.overview,
                    movie.releaseDate,
                    movie.id,
                    movie.originalTitle,
                    movie.title,
                    movie.originalLanguage,
                    movie.popularity,
                    movie.favourite,
                    movie.voteCount,
                    movie.adult,
                    movie.voteAverage
                )
            )
        }
    }
}
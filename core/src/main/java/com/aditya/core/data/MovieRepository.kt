package com.aditya.core.data

import com.aditya.core.data.source.local.LocalDataSource
import com.aditya.core.data.source.local.entity.MovieEntity
import com.aditya.core.data.source.remote.ApiResponse
import com.aditya.core.data.source.remote.RemoteDataSource
import com.aditya.core.data.source.remote.response.DetailMovieResponse
import com.aditya.core.data.source.remote.response.MovieData
import com.aditya.core.domain.model.Movie
import com.aditya.core.domain.repository.IMovieRepository
import com.aditya.core.utils.SortUtils
import com.aditya.core.utils.convertMovieEntityToDomainModel
import com.aditya.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository {

    override fun getAllMovie(sort: String): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<MovieData>>() {
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

            override suspend fun createCall(): Flow<ApiResponse<List<MovieData>>> {
                return remoteDataSource.getAllMovie()
            }

            override suspend fun saveCallResult(data: List<MovieData>) {
                localDataSource.insertMovies(data.map {
                    MovieEntity(
                        it.poster_path,
                        it.backdrop_path,
                        it.overview,
                        it.release_date,
                        it.id,
                        it.original_title,
                        it.title,
                        it.original_language,
                        it.popularity,
                        false,
                        it.vote_count,
                        it.adult,
                        it.vote_average
                    )
                })
            }

        }.asFlow()
    }

    override fun getMovieById(id: String): Flow<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, DetailMovieResponse>() {
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

            override suspend fun createCall(): Flow<ApiResponse<DetailMovieResponse>> {
                return remoteDataSource.getMovieById(id)
            }

            override suspend fun saveCallResult(data: DetailMovieResponse) {

            }

        }.asFlow()
    }

    override fun getAllMovieFavourite(): Flow<List<Movie>> {
        return localDataSource.getAllMovieFavourite().map { it.convertMovieEntityToDomainModel() }
    }

    override suspend fun setFavouriteMovie(movie: Movie, state: Boolean) {
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
package com.aditya.core.utils

import com.aditya.core.data.source.local.entity.MovieEntity
import com.aditya.core.domain.model.Movie

fun List<MovieEntity>.convertMovieEntityToDomainModel():List<Movie> {
    return map {
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
            false,
            it.voteCount,
            it.adult,
            it.voteAverage
        )
    }
}

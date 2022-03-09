package com.aditya.core.domain.model

import androidx.room.PrimaryKey

data class Movie(
    val posterPath:String,
    val backdropPath:String,
    val overview:String,
    val releaseDate:String,
    @PrimaryKey
    val id:Int,
    val originalTitle:String,
    val title:String,
    val originalLanguage:String,
    val popularity:Double,
    var favourite:Boolean,
    val voteCount:Int,
    val adult:Boolean,
    val voteAverage:Double
)

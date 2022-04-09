package com.aditya.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val results:List<MovieData>,
    @SerializedName("msg")
    val msg:String)

data class MovieData(
    @SerializedName("poster_path")
    val poster_path:String,
    @SerializedName("backdrop_path")
    val backdrop_path:String,
    @SerializedName("overview")
    val overview:String,
    @SerializedName("release_date")
    val release_date:String,
    @SerializedName("id")
    val id:Int,
    @SerializedName("original_title")
    val original_title:String,
    @SerializedName("title")
    val title:String,
    @SerializedName("original_language")
    val original_language:String,
    @SerializedName("popularity")
    val popularity:Double,
    @SerializedName("vote_count")
    val vote_count:Int,
    @SerializedName("adult")
    val adult:Boolean,
    @SerializedName("vote_average")
    val vote_average:Double
)

package com.aditya.core.data.source.remote.network

import com.aditya.core.data.source.remote.response.DetailMovieResponse
import com.aditya.core.data.source.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApi {

    @GET("discover/movie")
    suspend fun getAllMovie(): MovieResponse

    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") id:String): DetailMovieResponse
}
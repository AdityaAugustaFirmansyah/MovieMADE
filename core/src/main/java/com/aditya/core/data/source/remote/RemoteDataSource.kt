package com.aditya.core.data.source.remote

import com.aditya.core.data.source.remote.network.RestApi
import com.aditya.core.data.source.remote.response.DetailMovieResponse
import com.aditya.core.data.source.remote.response.MovieData
import com.aditya.core.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val restApi: RestApi) {

    fun getAllMovie():Flow<ApiResponse<List<MovieData>>>{
        return flow {
            try {
                val response = restApi.getAllMovie()
                val results = response.results
                emit(ApiResponse.success(results))
            }catch (e:Exception){
                emit(ApiResponse.error<List<MovieData>>(e.toString(), mutableListOf()))
            }
        }.flowOn(Dispatchers.IO)
    }
    fun getMovieById(id: String):Flow<ApiResponse<DetailMovieResponse>>{
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = restApi.getMovieById(id)
                emit(ApiResponse.success(response))
            } catch (e: Exception) {
                e.message?.let { ApiResponse.error<DetailMovieResponse>(it, null) }?.let {
                    emit(it)
                }
            }
        }.flowOn(Dispatchers.IO)
    }

}
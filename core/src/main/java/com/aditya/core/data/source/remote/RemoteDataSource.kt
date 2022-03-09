package com.aditya.core.data.source.remote

import com.aditya.core.data.source.remote.response.DetailMovieResponse
import com.aditya.core.data.source.remote.response.MovieResponse
import com.aditya.core.data.source.remote.network.RestApi
import com.aditya.core.utils.EspressoIdlingResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource (private val restApi: RestApi) {

    fun getAllMovie():Flow<ApiResponse<MovieResponse>>{
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = restApi.getAllMovie()
                emit(ApiResponse.success(response))
            }catch (e:Exception){
                e.message?.let { ApiResponse.error<MovieResponse>(it, null) }?.let {
                    emit(it)
                }
            }
        }
    }
    fun getMovieById(id: String):Flow<ApiResponse<DetailMovieResponse>>{
        EspressoIdlingResource.increment()
            return flow {
                try {
                    val response = restApi.getMovieById(id)
                    emit(ApiResponse.success(response))
                }catch (e:Exception){
                    e.message?.let { ApiResponse.error<DetailMovieResponse>(it, null) }?.let {
                        emit(it)
                    }
                }
            }
    }

}
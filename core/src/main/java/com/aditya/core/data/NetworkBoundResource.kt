package com.aditya.core.data

import com.aditya.core.data.source.remote.ApiResponse
import com.aditya.core.data.source.remote.StatusResponse
import com.aditya.core.utils.AppExecutors
import com.aditya.core.vo.Resource
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType,RequestType>(private val executors:AppExecutors) {
    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.loading())
        val dbSource = loadFromDb().first()
        if (shouldFetch(dbSource)){
            val apiResponse = createCall().first()
            when(apiResponse.status){
                StatusResponse.SUCCESS ->{
                    apiResponse.body?.let { saveCallResult(it) }
                    emitAll(loadFromDb().map { Resource.success(apiResponse.body) })
                }
                StatusResponse.EMPTY ->{
                    emitAll(loadFromDb().map { Resource.success(it) })
                }
                StatusResponse.ERROR ->{
                    onFetchFailed()
                    emit(Resource.error<ResultType>(apiResponse.message,null))
                }
            }
        }else{
            emitAll(loadFromDb().map { Resource.success(it) })
        }
    }

    private fun onFetchFailed(){}
    protected abstract fun loadFromDb():Flow<ResultType>
    protected abstract fun shouldFetch(data: ResultType?):Boolean
    protected abstract fun createCall():Flow<ApiResponse<RequestType>>
    protected abstract fun saveCallResult(data: RequestType)

    fun asFlow():Flow<Resource<ResultType>> =result
}
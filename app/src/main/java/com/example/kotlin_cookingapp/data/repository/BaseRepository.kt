package com.example.kotlin_cookingapp.data.repository

import com.example.kotlin_cookingapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// This class is used in order to make our code reusable.
// this method's only function is to invoke our Api.
// allows us to call our Api methods we have defined in remote.api.Api interface
// implementation of the repository of the domain layer
abstract class BaseRepository {

    suspend fun <T> invokeApi(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO){
            try{
                Resource.Success(apiCall.invoke()) // if success apiCall.invoke()
            }catch (throwable: Throwable){
                Resource.Error(error = throwable) // if fail error
            }
        }
    }
}
package com.example.mybaseprojectwithhilt.data.source.remote

import com.example.mybaseprojectwithhilt.data.model.Country
import com.example.mybaseprojectwithhilt.data.source.remote.service.ApiService
import com.example.mybaseprojectwithhilt.data.source.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class RemoteDataSource internal constructor(private val apiService: ApiService,private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO):BaseRemoteDataSource  {
    companion object{
        var token=""
    }
    override suspend fun getCountryList(): Result<List<Country>> {
        return try {
            apiService.getCountries(token).let {response->
                if(response.isSuccessful){
                    when {
                        response.code()==200 ->
                            return Result.Success(
                                response.body()!!.data!!
                            )
                        else ->
                            return Result.Error(Exception("Unhandled http status code returned"))
                    }
                }else return Result.Error(Exception("Error occured"))
            }
        } catch (cause: Exception) {
            return Result.Error(cause)
        }
    }
}
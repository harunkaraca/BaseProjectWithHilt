package com.example.mybaseprojectwithhilt.data.source

import com.example.mybaseprojectwithhilt.data.model.Country
import com.example.mybaseprojectwithhilt.data.source.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import java.lang.IllegalStateException
import com.example.mybaseprojectwithhilt.data.source.Result
import com.example.mybaseprojectwithhilt.data.source.local.LocalDataSource
import com.example.mybaseprojectwithhilt.util.wrapEspressoIdlingResource
import kotlinx.coroutines.withContext

class DefaultRepository(
    private val remoteDataSource:RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO):BaseRepository {

    override suspend fun getCountryList(): Result<List<Country>> {
        wrapEspressoIdlingResource {
            return withContext(ioDispatcher){
                val countryList=fetchCountriesFromRemote()
                (countryList as? Result.Success)?.let {
                    return@withContext Result.Success(it.data)
                }
                return@withContext Result.Error(Exception("Illegal state"))
            }
        }
    }
    private suspend fun fetchCountriesFromRemote() : Result<List<Country>> {
        val countryList=remoteDataSource.getCountryList()
        when(countryList){
            is Result.Error->
                Timber.w("Remote data source fetch failed "+countryList.exception)
            is Result.Success ->{
                return countryList
            }
            else->throw IllegalStateException()
        }
        return Result.Error(Exception("Error fetching from remote"))
    }
    private suspend fun refreshCountriesLocalDataSource(countries: List<Country>) {
        localDataSource.deleteAllCountries()
        for (country in countries) {
            localDataSource.saveCountry(country)
        }
    }
}
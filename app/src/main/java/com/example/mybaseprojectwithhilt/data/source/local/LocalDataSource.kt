package com.example.mybaseprojectwithhilt.data.source.local

import com.example.mybaseprojectwithhilt.data.model.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.mybaseprojectwithhilt.data.source.Result
import com.example.mybaseprojectwithhilt.data.source.local.entity.CountryTable

class LocalDataSource internal constructor(
    private val countryDao: CountryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
):BaseLocalDataSource {


    override suspend fun getCountryList(): Result<List<Country>> = withContext(ioDispatcher) {
        return@withContext try {
            val countries:List<Country> = countryDao.getCountryList().map { Country( it.id,it.countryName,it.capital,it.flagUrl) }
            Result.Success(countries)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getCountryById(id: Int): Result<Country> = withContext(ioDispatcher) {
        try {
            val countryTable=countryDao.getCountryById(id)
            if(countryTable!=null){
                val country:Country =  Country( countryTable.id,countryTable.countryName,countryTable.capital,countryTable.flagUrl)
                return@withContext Result.Success(country)
            }else{
                return@withContext Result.Error(Exception("No country found"))
            }

        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun deleteCountry(id: Int) {
        countryDao.deleteCountryById(id)
    }

    override suspend fun deleteAllCountries() {
        countryDao.deleteAllCountry()
    }

    override suspend fun saveCountry(country: Country) {
        val countryT = CountryTable(country.countryName,country.capital,country.flagUrl,country.id)
        countryDao.insertCountry(countryT)
    }
}
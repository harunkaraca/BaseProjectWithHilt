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
) {


    suspend fun getCountryList(): Result<List<Country>> = withContext(ioDispatcher) {
        return@withContext try {
            val countries:List<Country> = countryDao.getCountryList().map { Country( it.id,it.countryName,it.capital,it.flagUrl) }
            Result.Success(countries)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun deleteCountry(id: Int) {
        countryDao.deleteCountryById(id)
    }

    suspend fun deleteAllCountries() {
        countryDao.deleteAllCountry()
    }

    suspend fun saveCountry(country: Country) {
        val countryT = CountryTable(country.countryName,country.capital,country.flagUrl,country.id)
        countryDao.insertCountry(countryT)
    }
}
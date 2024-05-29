package com.example.mybaseprojectwithhilt.data.source.local

import com.example.mybaseprojectwithhilt.data.model.Country
import com.example.mybaseprojectwithhilt.data.source.Result

interface BaseLocalDataSource {
    suspend fun getCountryList(): Result<List<Country>>
    suspend fun getCountryById(id: Int): Result<Country>
    suspend fun deleteCountry(id: Int)
    suspend fun deleteAllCountries()
    suspend fun saveCountry(country: Country)
}
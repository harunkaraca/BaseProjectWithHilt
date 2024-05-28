package com.example.mybaseprojectwithhilt.data.source

import com.example.mybaseprojectwithhilt.data.model.Country
import com.example.mybaseprojectwithhilt.data.source.local.BaseLocalDataSource
import com.example.mybaseprojectwithhilt.data.source.remote.BaseRemoteDataSource

class FakeLocalDataSource(var countries: MutableList<Country>? = mutableListOf()) : BaseLocalDataSource {
    override suspend fun getCountryList(): Result<List<Country>> {
        countries?.let { return Result.Success(ArrayList(it)) }
        return Result.Error(
            Exception("Tasks not found")
        )
    }

    override suspend fun deleteCountry(id: Int) {
        countries?.removeIf{it.id==id}
    }

    override suspend fun deleteAllCountries() {
        countries?.clear()
    }

    override suspend fun saveCountry(country: Country) {
        countries?.add(country)
    }
}
package com.example.mybaseprojectwithhilt.data.source

import com.example.mybaseprojectwithhilt.data.model.Country
import com.example.mybaseprojectwithhilt.data.source.remote.BaseRemoteDataSource

class FakeRemoteDataSource(var countries: MutableList<Country>? = mutableListOf()) : BaseRemoteDataSource {
    override suspend fun getCountryList(): Result<List<Country>> {
        countries?.let { return Result.Success(ArrayList(it)) }
        return Result.Error(
            Exception("Tasks not found")
        )
    }
}
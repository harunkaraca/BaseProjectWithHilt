package com.example.mybaseprojectwithhilt.data.source.remote

import com.example.mybaseprojectwithhilt.data.model.Country
import com.example.mybaseprojectwithhilt.data.source.Result

interface BaseRemoteDataSource {
    suspend fun getCountryList(): Result<List<Country>>
}
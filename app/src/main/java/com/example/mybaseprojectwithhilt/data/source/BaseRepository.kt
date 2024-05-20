package com.example.mybaseprojectwithhilt.data.source

import com.example.mybaseprojectwithhilt.data.model.Country

interface BaseRepository {
    suspend fun getCountryList(): Result<List<Country>>
}
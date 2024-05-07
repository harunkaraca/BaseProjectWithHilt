package com.example.mybaseprojectwithhilt.data

import com.example.mybaseprojectwithhilt.data.model.Country
import com.example.mybaseprojectwithhilt.data.source.Result

interface DataSource {
    suspend fun getCountryList(): Result<List<Country>>
}
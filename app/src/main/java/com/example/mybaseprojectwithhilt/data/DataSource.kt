package com.example.mybaseprojectwithhilt.data

import com.example.mybaseprojectwithhilt.data.model.Country

interface DataSource {
    suspend fun getCountryList(): Result<List<Country>>
}
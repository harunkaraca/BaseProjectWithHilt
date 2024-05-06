package com.example.mybaseprojectwithhilt.data.source.remote.service

import com.example.mybaseprojectwithhilt.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("/harunkaraca/mockdata/main/mock.json")
    suspend fun getCountries(@Header("token") token:String): Response<BaseApiResponse<List<Country>>>

}
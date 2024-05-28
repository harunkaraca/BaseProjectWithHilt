package com.example.mybaseprojectwithhilt

import androidx.lifecycle.MutableLiveData
import com.example.mybaseprojectwithhilt.data.model.Country
import com.example.mybaseprojectwithhilt.data.source.BaseRepository
import com.example.mybaseprojectwithhilt.data.source.Result
import java.util.LinkedHashMap
import javax.inject.Inject

class FakeRepository @Inject constructor() : BaseRepository {
    var serviceData: LinkedHashMap<String, Country> = LinkedHashMap()

    private var shouldReturnError = false

    private val observableTasks = MutableLiveData<Result<List<Country>>>()

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }
    override suspend fun getCountryList(): Result<List<Country>> {
        if (shouldReturnError) {
            return Result.Error(Exception("Test exception"))
        }
        return Result.Success(serviceData.values.toList())
    }
}
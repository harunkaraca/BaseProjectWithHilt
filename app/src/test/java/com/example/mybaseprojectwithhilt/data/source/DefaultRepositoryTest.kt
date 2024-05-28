package com.example.mybaseprojectwithhilt.data.source

import com.example.mybaseprojectwithhilt.MainCoroutineRule
import com.example.mybaseprojectwithhilt.data.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultRepositoryTest {
    private val country1 = Country(1, "TestCountryName1","Capital1","")
    private val country2 = Country(2, "TestCountryName2","Capital2","")
    private val country3 = Country(3, "TestCountryName3","Capital3","")
    private val newCountry = Country(4, "TestCountryName3 new","Capital3 new","")
    private val countries = listOf(country1, country2,country3).sortedBy { it.id }
    private val newCountries = listOf(newCountry).sortedBy { it.id }
    private lateinit var remoteDataSource: FakeRemoteDataSource
    private lateinit var localDataSource: FakeLocalDataSource
    // Class under test
    private lateinit var defaultRepository: DefaultRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @ExperimentalCoroutinesApi
    @Before
    fun createRepository() {
        remoteDataSource = FakeRemoteDataSource(countries.toMutableList())
        localDataSource = FakeLocalDataSource(countries.toMutableList())
        // Get a reference to the class under test
        defaultRepository = DefaultRepository(
            remoteDataSource, localDataSource, Dispatchers.Main
        )
    }

    @Test
    fun `Get remote data source`()= runTest {
        // When countries are requested from the default repository
        val _countries = defaultRepository.getCountryList() as Result.Success
        // Then countries are loaded from the remote data source
        assertThat(_countries.data).isEqualTo(countries)
    }
}
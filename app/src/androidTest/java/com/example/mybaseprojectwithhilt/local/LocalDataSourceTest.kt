package com.example.mybaseprojectwithhilt.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.filters.SmallTest
import com.example.mybaseprojectwithhilt.MainCoroutineRule
import com.example.mybaseprojectwithhilt.data.model.Country
import com.example.mybaseprojectwithhilt.data.source.local.LocalDataSource
import com.example.mybaseprojectwithhilt.data.source.local.MyDatabase
import com.example.mybaseprojectwithhilt.data.source.local.entity.CountryTable
import com.example.mybaseprojectwithhilt.data.source.succeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.mybaseprojectwithhilt.data.source.Result


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class LocalDataSourceTest {
    private lateinit var database: MyDatabase
    private lateinit var localDataSource:LocalDataSource
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            MyDatabase::class.java
        ).allowMainThreadQueries().build()
        localDataSource=LocalDataSource(database.countryDao(),Dispatchers.Main)
    }

    @After
    fun cleanUp() = database.close()
    @Test
    fun getCountryList() = runTest {
        val country = Country(1,"countryName", "capital","flagUrl")
        localDataSource.saveCountry(country)

        val result = localDataSource.getCountryById(country.id)

        assertThat(result.succeeded, CoreMatchers.`is`(true))
        result as Result.Success
        assertThat(result.data.countryName, CoreMatchers.`is`("countryName"))
        assertThat(result.data.capital, CoreMatchers.`is`("capital"))
        assertThat(result.data.flagUrl, CoreMatchers.`is`("flagUrl"))
    }
    @Test
    fun getCountryById() = runTest {
        val country = Country(1,"countryName", "capital","flagUrl")
        localDataSource.saveCountry(country)

        val result = localDataSource.getCountryById(country.id)

        assertThat(result.succeeded, CoreMatchers.`is`(true))
        result as Result.Success
        assertThat(result.data.countryName, CoreMatchers.`is`("countryName"))
        assertThat(result.data.capital, CoreMatchers.`is`("capital"))
        assertThat(result.data.flagUrl, CoreMatchers.`is`("flagUrl"))
    }
    @Test
    fun deleteCountry() = runTest{
        val country = Country(1,"countryName", "capital","flagUrl")
        localDataSource.saveCountry(country)

        localDataSource.deleteCountry(country.id)
        val result =localDataSource.getCountryById(country.id)
        result as Result.Error
    }
    @Test
    fun deleteAllCountries()= runTest {
        val country = Country(1,"countryName", "capital","flagUrl")
        localDataSource.saveCountry(country)
        localDataSource.deleteAllCountries()

        val result = localDataSource.getCountryList()
        result as Result.Success
        assertThat(result.data.isEmpty(), CoreMatchers.`is`(true))
    }
    @Test
    fun saveCountry()= runTest {
        val country = Country(1,"countryName", "capital","flagUrl")
        localDataSource.saveCountry(country)
        val countries = localDataSource.getCountryList()
        countries as Result.Success
        assertThat(countries.data.size, CoreMatchers.`is`(1))
        assertThat(countries.data[0].id, CoreMatchers.`is`(country.id))
        assertThat(countries.data[0].countryName, CoreMatchers.`is`(country.countryName))
        assertThat(countries.data[0].capital, CoreMatchers.`is`(country.capital))
        assertThat(countries.data[0].flagUrl, CoreMatchers.`is`(country.flagUrl))
    }
}
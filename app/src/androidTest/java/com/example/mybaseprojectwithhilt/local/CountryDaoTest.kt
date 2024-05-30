package com.example.mybaseprojectwithhilt.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.mybaseprojectwithhilt.MainCoroutineRule
import com.example.mybaseprojectwithhilt.data.source.local.MyDatabase
import com.example.mybaseprojectwithhilt.data.source.local.entity.CountryTable
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CountryDaoTest {
    private lateinit var database: MyDatabase

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            MyDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()


    @Test
    fun insertCountryAndGetCountryById() = runTest {
        val country = CountryTable("countryName", "capital","flagUrl")
        database.countryDao().insertCountry(country)
        val loaded = database.countryDao().getCountryById(country.id)
        assertThat<CountryTable>(loaded as CountryTable, notNullValue())
        assertThat(loaded.id, `is`(country.id))
        assertThat(loaded.countryName, `is`(country.countryName))
        assertThat(loaded.capital, `is`(country.capital))
        assertThat(loaded.flagUrl, `is`(country.flagUrl))
    }

    @Test
    fun insertCountryAndGetCountries() = runTest {
        val country = CountryTable("countryName", "capital","flagUrl")
        database.countryDao().insertCountry(country)

        val countries = database.countryDao().getCountryList()
        assertThat(countries.size, `is`(1))
        assertThat(countries[0].id, `is`(country.id))
        assertThat(countries[0].countryName, `is`(country.countryName))
        assertThat(countries[0].capital, `is`(country.capital))
        assertThat(countries[0].flagUrl, `is`(country.flagUrl))
    }

    @Test
    fun updateCountryAndGetById() = runTest {
        val originalCountry = CountryTable("countryName", "capital","flagUrl")
        database.countryDao().insertCountry(originalCountry)

        val updatedCountry = CountryTable("new countryName", "new capital","new flagUrl",originalCountry.id)
        database.countryDao().updateCountry(updatedCountry)

        val loaded = database.countryDao().getCountryById(originalCountry.id)
        assertThat(loaded?.id, `is`(originalCountry.id))
        assertThat(loaded?.countryName, `is`("new countryName"))
        assertThat(loaded?.capital, `is`("new capital"))
        assertThat(loaded?.flagUrl, `is`("new flagUrl"))
    }

    @Test
    fun deleteCountryByIdAndGettingCountries() = runTest {
        val country = CountryTable("countryName", "capital","flagUrl")
        database.countryDao().insertCountry(country)
        database.countryDao().deleteCountryById(country.id)

        val countries = database.countryDao().getCountryList()
        assertThat(countries.isEmpty(), `is`(true))
    }

    @Test
    fun deleteCountriesAndGettingCountries() = runTest {
        val country = CountryTable("countryName", "capital","flagUrl")
        database.countryDao().insertCountry(country)
        database.countryDao().deleteAllCountry()

        // THEN - The list is empty
        val countries = database.countryDao().getCountryList()
        assertThat(countries.isEmpty(), `is`(true))
    }
}
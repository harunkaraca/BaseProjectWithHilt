package com.example.mybaseprojectwithhilt.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mybaseprojectwithhilt.data.source.local.entity.CountryTable

@Dao
interface CountryDao {
    /**
     * Select all country from the country table.
     *
     * @return all country.
     */
    @Query("Select * from COUNTRY")
    suspend fun getCountryList():List<CountryTable>

    /**
     * Select a country by id.
     *
     * @param countryId the country id.
     * @return the country with countryId.
     */
    @Query("SELECT * FROM COUNTRY WHERE id = :id")
    suspend fun getCountryById(id: Int): CountryTable?

    /**
     * Insert a country in the database. If the country already exists, replace it.
     *
     * @param countryT the country to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(countryT: CountryTable)

    /**
     * Update a country.
     *
     * @param countryT countryT to be updated
     * @return the number of country updated. This should always be 1.
     */
    @Update
    suspend fun updateCountry(countryT: CountryTable):Int

    /**
     * Delete a country by id.
     *
     * @return the number of country deleted. This should always be 1.
     */
    @Query("DELETE FROM country WHERE id = :id")
    suspend fun deleteCountryById(id: Int): Int

    /**
     * Delete all country.
     */
    @Query("DELETE FROM country")
    suspend fun deleteAllCountry()
}
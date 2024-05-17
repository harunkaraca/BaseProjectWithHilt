package com.example.mybaseprojectwithhilt.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mybaseprojectwithhilt.data.source.local.entity.CountryTable

@Database(entities = [CountryTable::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao
}
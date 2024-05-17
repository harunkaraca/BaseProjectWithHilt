package com.example.mybaseprojectwithhilt.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class CountryTable @JvmOverloads constructor(
    @ColumnInfo(name = "country_name") var countryName: String = "",
    @ColumnInfo(name = "capital") var capital: String = "",
    @ColumnInfo(name = "flag_url") var flagUrl: String? = "",
    @PrimaryKey @ColumnInfo(name = "id") var id: Int = 0
) {

}
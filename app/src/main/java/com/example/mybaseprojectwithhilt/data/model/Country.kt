package com.example.mybaseprojectwithhilt.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("name")
    @Expose
    var countryName: String,
    @SerializedName("capital")
    @Expose
    var capital: String,
    @SerializedName("flag_url")
    @Expose
    var flagUrl: String?
)
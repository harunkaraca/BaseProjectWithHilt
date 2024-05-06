package com.example.mybaseprojectwithhilt.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseApiResponse<T> (
    @SerializedName("message")
    @Expose
    val message : String?,
    @SerializedName("successCode")
    @Expose
    val successCode : Int?,
    @SerializedName("data")
    @Expose
    val data : T?
)

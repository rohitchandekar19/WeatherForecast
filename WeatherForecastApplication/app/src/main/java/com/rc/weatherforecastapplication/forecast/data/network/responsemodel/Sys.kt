package com.rc.weatherforecastapplication.forecast.data.network.responsemodel


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("id")
    val id: Int,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("type")
    val type: Int
)
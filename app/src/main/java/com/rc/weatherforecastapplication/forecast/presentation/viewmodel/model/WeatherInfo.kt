package com.rc.weatherforecastapplication.forecast.presentation.viewmodel.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherInfo(
    val city: String,
    val lat: Double,
    val longitude: Double,
    val clouds: Int,
    val sunrise: Long,
    val sunset: Long,
    val windSpeed: Double,
    val description: String,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double,
    val lastUpdatedAt: Long,
    val country: String,
    val icon: String,
    val status: String
) : Parcelable

package com.rc.weatherforecastapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Weather")
data class Weather(
    @PrimaryKey @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "clouds") val clouds: Int,
    @ColumnInfo(name = "sunrise") val sunrise: Long,
    @ColumnInfo(name = "sunset") val sunset: Long,
    @ColumnInfo(name = "windSpeed") val windSpeed: Double,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "humidity") val humidity: Int,
    @ColumnInfo(name = "pressure") val pressure: Int,
    @ColumnInfo(name = "temp") val temp: Double,
    @ColumnInfo(name = "tempMax") val tempMax: Double,
    @ColumnInfo(name = "tempMin") val tempMin: Double,
    @ColumnInfo(name = "lastUpdatedAt") val lastUpdatedAt: Long,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "status") val status: String
)

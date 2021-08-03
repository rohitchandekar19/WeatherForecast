package com.rc.weatherforecastapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * FROM Weather")
    suspend fun getWeatherInfoOfLocation(): Weather?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: Weather)

    @Query("DELETE FROM Weather")
    suspend fun deleteAll()
}
package com.rc.weatherforecastapplication.forecast.data.repository

import android.location.Location
import android.util.Log
import com.rc.weatherforecastapplication.database.Weather
import com.rc.weatherforecastapplication.database.WeatherDao
import com.rc.weatherforecastapplication.forecast.data.network.mapper.WeatherResponseMapper
import com.rc.weatherforecastapplication.forecast.data.network.service.WhetherService
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherService: WhetherService,
    private val weatherDao: WeatherDao,
    private val weatherResponseMapper: WeatherResponseMapper
) {

    suspend fun getWeatherConditionsOfLocation(location: Location?): Weather {
        val weather = weatherDao.getWeatherInfoOfLocation()
        var lat = 0.0
        var long = 0.0
        if (location == null && weather != null) {
            lat = weather.lat
            long = weather.longitude
        }
        return if (weather == null || location == null) {
            Log.d("REPO", "Getting data from Server")
            val weatherData =
                weatherResponseMapper.mapToViewModel(weatherService.fetchWeatherReport(lat, long))
            weatherDao.insert(weatherData)
            weatherData
        } else {
            Log.d("REPO", "Getting data from DB")
            weather
        }
    }
}
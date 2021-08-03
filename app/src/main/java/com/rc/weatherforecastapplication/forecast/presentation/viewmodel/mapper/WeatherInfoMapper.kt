package com.rc.weatherforecastapplication.forecast.presentation.viewmodel.mapper

import com.rc.weatherforecastapplication.database.Weather
import com.rc.weatherforecastapplication.forecast.presentation.viewmodel.model.WeatherInfo
import javax.inject.Inject

class WeatherInfoMapper @Inject constructor() {
    fun mapToViewModel(weatherData: Weather): WeatherInfo =
        WeatherInfo(
            weatherData.city,
            weatherData.lat,
            weatherData.longitude,
            weatherData.clouds,
            weatherData.sunrise,
            weatherData.sunset,
            weatherData.windSpeed,
            weatherData.description,
            weatherData.humidity,
            weatherData.pressure,
            weatherData.temp,
            weatherData.tempMax,
            weatherData.tempMin,
            weatherData.lastUpdatedAt,
            weatherData.country,
            weatherData.icon,
            weatherData.status
        )
}
package com.rc.weatherforecastapplication.forecast.data.network.mapper

import com.rc.weatherforecastapplication.database.Weather
import com.rc.weatherforecastapplication.forecast.data.network.responsemodel.WeatherResponseEntity
import javax.inject.Inject

class WeatherResponseMapper @Inject constructor() {
    fun mapToEntityModel(weatherReport: WeatherResponseEntity): Weather {
        return Weather(
            weatherReport.name,
            weatherReport.coord.lat,
            weatherReport.coord.lon,
            weatherReport.clouds.all,
            weatherReport.sys.sunrise,
            weatherReport.sys.sunset,
            weatherReport.wind.speed,
            weatherReport.weather[0].description,
            weatherReport.main.humidity,
            weatherReport.main.pressure,
            weatherReport.main.temp,
            weatherReport.main.tempMax,
            weatherReport.main.tempMin,
            weatherReport.dt,
            weatherReport.sys.country,
            weatherReport.weather[0].icon,
            weatherReport.weather[0].main
        )
    }
}
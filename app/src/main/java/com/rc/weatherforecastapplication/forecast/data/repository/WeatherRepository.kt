package com.rc.weatherforecastapplication.forecast.data.repository

import android.location.Location
import com.rc.weatherforecastapplication.database.WeatherDao
import com.rc.weatherforecastapplication.forecast.data.network.mapper.WeatherResponseMapper
import com.rc.weatherforecastapplication.forecast.data.network.service.WhetherService
import com.rc.weatherforecastapplication.forecast.presentation.viewmodel.mapper.WeatherInfoMapper
import com.rc.weatherforecastapplication.forecast.presentation.viewmodel.model.WeatherInfo
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherService: WhetherService,
    private val weatherDao: WeatherDao,
    private val weatherResponseMapper: WeatherResponseMapper,
    private val weatherInfoMapper: WeatherInfoMapper
) {

    suspend fun getWeatherConditionsOfLocation(
        location: Location?,
        isRequestFromWorkManager: Boolean
    ): WeatherInfo? {

        val weather = weatherDao.getWeatherInfoOfLocation()

        if (location == null && weather == null) {
            return null
        }

        val lat: Double
        val long: Double

        if (weather != null) {
            lat = weather.lat
            long = weather.longitude
        } else {
            lat = location!!.latitude
            long = location.longitude
        }

        return if (isRequestFromWorkManager) {
            //Fetch weather status from Server
            getDataFromServer(lat, long)
        } else {
            if (weather != null) {
                // Send back the cached data from DB
                weatherInfoMapper.mapToViewModel(weather)
            } else {
                getDataFromServer(lat, long)
            }
        }
    }

    private suspend fun getDataFromServer(lat: Double, long: Double): WeatherInfo? {
        val weatherData =
            weatherResponseMapper.mapToEntityModel(weatherService.fetchWeatherReport(lat, long))
        weatherDao.insert(weatherData)
        return weatherInfoMapper.mapToViewModel(weatherData)
    }
}
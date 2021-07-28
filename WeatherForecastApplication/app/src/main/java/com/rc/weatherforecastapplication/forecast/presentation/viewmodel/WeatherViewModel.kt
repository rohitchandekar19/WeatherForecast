package com.rc.weatherforecastapplication.forecast.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rc.weatherforecastapplication.database.Weather
import com.rc.weatherforecastapplication.forecast.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val weatherMutableLiveData = MutableLiveData<Weather>()
    val weatherLiveData: LiveData<Weather> get() = weatherMutableLiveData

    fun getWeatherConditionsOfLocation(location: Location) {
        viewModelScope.launch {
            val weather = weatherRepository.getWeatherConditionsOfLocation(location)
            withContext(Dispatchers.Main) {
                weatherMutableLiveData.value = weather
            }
        }
    }
}
package com.rc.weatherforecastapplication.forecast.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rc.weatherforecastapplication.forecast.data.repository.WeatherRepository
import com.rc.weatherforecastapplication.forecast.presentation.viewmodel.model.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val weatherMutableLiveData = MutableLiveData<WeatherInfo?>()
    val weatherLiveData: LiveData<WeatherInfo?> get() = weatherMutableLiveData

    /**
     * This is used to fetch the weather status of the given location
     */
    fun getWeatherConditionsOfLocation(location: Location) {
        viewModelScope.launch {
            val weather = weatherRepository.getWeatherConditionsOfLocation(location, false)
            withContext(Dispatchers.Main) {
                weatherMutableLiveData.value = weather
            }
        }
    }
}
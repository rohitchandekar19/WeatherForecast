package com.rc.weatherforecastapplication.forecast.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rc.weatherforecastapplication.R
import com.rc.weatherforecastapplication.databinding.ActivityMainBinding
import com.rc.weatherforecastapplication.forecast.presentation.viewmodel.WeatherViewModel
import com.rc.weatherforecastapplication.getDisplayDateTime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        obtainCurrentLocation()
        observerWeatherConditions()
    }

    private fun observerWeatherConditions() {
        weatherViewModel.weatherLiveData.observe(this, {
            mainBinding.weatherTextView.text =
                getString(
                    R.string.details_message,
                    it.city,
                    it.humidity,
                    it.pressure,
                    it.sunrise.getDisplayDateTime(),
                    it.sunset.getDisplayDateTime(),
                    it.temp,
                    it.tempMax,
                    it.tempMin,
                    it.windSpeed,
                    it.description,
                    it.lastUpdatedAt.getDisplayDateTime()
                )
        })
    }

    private fun obtainCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Don't have permissions", Toast.LENGTH_SHORT).show()
            return
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener {
                    weatherViewModel.getWeatherConditionsOfLocation(it)
                }
        }
    }
}
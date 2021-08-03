package com.rc.weatherforecastapplication.forecast.presentation.view

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
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
    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener {
                        weatherViewModel.getWeatherConditionsOfLocation(it)
                    }
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    // This condition only becomes true if the user has denied the permission previously
                    showDialogOK(
                        getString(R.string.on_denied_error_message)
                    ) { _, which ->
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE -> obtainCurrentLocation()
                        }
                    }
                } else {
                    explain(getString(R.string.mandatory_permission_explanation_text))
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.hide()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        obtainCurrentLocation()
        observerWeatherConditions()
    }

    private fun observerWeatherConditions() {
        weatherViewModel.weatherLiveData.observe(this, { weather ->
            if (weather == null) {
                Toast.makeText(this, getString(R.string.generic_error), Toast.LENGTH_LONG).show()
            } else {
                Glide.with(this).load(STATUS_URI.plus(weather.icon))
                    .into(binding.statusImageView)
                binding.cityNameTextView.text = weather.city.plus(", ${weather.country}")
                binding.humidityTextView.text = getString(R.string.humidity_text, weather.humidity)
                binding.windTextView.text = getString(R.string.wind_speed_text, weather.windSpeed)
                binding.pressureTextView.text = getString(R.string.pressure_text, weather.pressure)
                binding.temperatureTextView.text = getString(R.string.temp_text, weather.temp)
                binding.maxTempTextView.text = getString(R.string.temp_text, weather.tempMax)
                binding.minTempTextView.text = getString(R.string.temp_text, weather.temp)
                binding.descriptionTextView.text = weather.description
                binding.feelsLikeTextView.text = getString(R.string.temp_text, weather.feelsLike)
                binding.dateTextView.text = weather.lastUpdatedAt.getDisplayDateTime()
            }
        })
    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .create()
            .show()
    }

    /**
     * Show explanation, why this permission is needed
     */
    private fun explain(msg: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(msg)
            .setPositiveButton("Yes") { _, _ ->
                startActivity(Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", packageName, null)
                })
            }
            .setNegativeButton("Cancel") { _, _ ->
                dialog.create().dismiss()
                finish()
            }
        dialog.show()
    }

    /**
     *  Use to get the current location by checking the permissions
     */
    private fun obtainCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Ask for the permission again
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            // Get the current location and do weather API call
            fusedLocationClient.lastLocation
                .addOnSuccessListener {
                    weatherViewModel.getWeatherConditionsOfLocation(it)
                }
        }
    }

    companion object {
        private const val STATUS_URI = "http://api.openweathermap.org/img/w/"
    }
}
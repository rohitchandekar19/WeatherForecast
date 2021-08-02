package com.rc.weatherforecastapplication.forecast.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rc.weatherforecastapplication.forecast.data.repository.WeatherRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters
) : Worker(appContext, params) {

    @Inject
    lateinit var weatherRepository: WeatherRepository

    override fun doWork(): Result {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("WorkManager", "Work manager called.")
                weatherRepository.getWeatherConditionsOfLocation(null)
            }
        } catch (e: Exception) {
            return Result.retry()
        }
        return Result.success()
    }
}
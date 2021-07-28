package com.rc.weatherforecastapplication

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.rc.weatherforecastapplication.forecast.work.WeatherWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class ForecastApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        setupRecurringWork()
    }

    private fun setupRecurringWork() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<WeatherWorker>(2, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        Log.d("Periodic WorkManager", "Periodic Work request for sync is scheduled")
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            WeatherWorker::class.java.canonicalName!!,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}
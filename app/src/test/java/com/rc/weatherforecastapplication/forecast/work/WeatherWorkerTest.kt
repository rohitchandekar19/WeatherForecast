package com.rc.weatherforecastapplication.forecast.work

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestWorkerBuilder
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class WeatherWorkerTest {
    @Mock
    private lateinit var context: Context
    private lateinit var executor: Executor

    @Before
    fun setUp() {
        executor = Executors.newSingleThreadExecutor()
    }

    @Test
    fun testWeatherWorker() {
        val worker = TestWorkerBuilder<WeatherWorker>(
            context = context,
            executor = executor
        ).build()

        val result = worker.doWork()
        assertThat(result, `is`(ListenableWorker.Result.success()))
    }
}
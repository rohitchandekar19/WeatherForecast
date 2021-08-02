package com.rc.weatherforecastapplication.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rc.weatherforecastapplication.network.CurlLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkDataModule {
    private const val BASE_URL = "http://api.openweathermap.org"
    private const val TIME_OUT = 2L

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.MINUTES)
            .writeTimeout(TIME_OUT, TimeUnit.MINUTES)
            .readTimeout(TIME_OUT, TimeUnit.MINUTES)
            .build()
    }

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    @Provides
    fun providedGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder(
        okHttpClient: OkHttpClient,
        interceptor: HttpLoggingInterceptor,
        gson: Gson
    ): Retrofit.Builder {
        val interceptClient = okHttpClient.newBuilder()
            .addNetworkInterceptor(CurlLoggingInterceptor)
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder().client(interceptClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }
}
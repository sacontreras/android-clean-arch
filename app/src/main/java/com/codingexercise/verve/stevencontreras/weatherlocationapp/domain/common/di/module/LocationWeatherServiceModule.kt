package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.module

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi.LocationWeatherService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class LocationWeatherServiceModule {
    companion object {
        const val BASE_URL: String = "https://api.openweathermap.org"
    }

    @Provides //roughly equivalent to "@Bean" in Spring
    fun provideOkhttpClient(): OkHttpClient {
        val httpLoggingIntercepter = HttpLoggingInterceptor()
        httpLoggingIntercepter.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(httpLoggingIntercepter).build()
    }

    @Provides
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) //maps body to json representation of LocationWeatherResponse model
            .build()
    }

    @Provides
    fun provideLocationWeatherService(): LocationWeatherService {
        return provideRetrofit(BASE_URL, provideOkhttpClient()).create(LocationWeatherService::class.java)
    }
}
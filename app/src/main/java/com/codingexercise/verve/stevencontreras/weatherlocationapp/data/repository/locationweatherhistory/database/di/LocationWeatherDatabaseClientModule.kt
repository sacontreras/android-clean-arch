package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.di

import android.content.Context
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.LocationWeatherDatabaseClient
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherHistoryRepository
import dagger.Module
import dagger.Provides

@Module
class LocationWeatherDatabaseClientModule(private val context: Context) {
    @Provides
    fun provideLocationWeatherHistoryRepository(): LocationWeatherHistoryRepository {
        return LocationWeatherDatabaseClient(context)
    }
}
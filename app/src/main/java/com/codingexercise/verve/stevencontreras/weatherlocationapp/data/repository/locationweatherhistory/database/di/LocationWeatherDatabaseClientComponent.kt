package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(LocationWeatherDatabaseClientModule::class))
interface LocationWeatherDatabaseClientComponent
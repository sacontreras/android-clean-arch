package com.codingexercise.verve.stevencontreras.weatherlocationapp

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherLocAppModule(private val application: Application) {
    @Provides
    @Singleton
    fun provideApp(): Application {
        return application
    }
}
package com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun provideApp(): Application {
        return application
    }
}
package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationprovider.android.di

import android.content.Context
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationprovider.android.AndroidLocationUpdateProvider
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationUpdateProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidLocationUpdateProviderModule(private val context: Context) {
    @Singleton
    @Provides
    fun provideLocationUpdateProvider(): LocationUpdateProvider {
        return AndroidLocationUpdateProvider(context)
    }
}
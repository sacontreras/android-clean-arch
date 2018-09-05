package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherprovider.webserviceclient.di

import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherprovider.webserviceclient.LocationWeatherWebServiceClient
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherProvider
import dagger.Module
import dagger.Provides

@Module
class LocationWeatherWebServiceClientModule(private val key: String = "") {
    @Provides
    fun provideLocationWeatherProviderRepository(): LocationWeatherProvider {
        return LocationWeatherWebServiceClient(key)
    }
}
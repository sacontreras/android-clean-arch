package com.codingexercise.verve.stevencontreras.weatherlocationapp

import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationprovider.android.di.AndroidLocationUpdateProviderModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.di.LocationWeatherDatabaseClientModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherprovider.webserviceclient.di.LocationWeatherWebServiceClientModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.presentation.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        WeatherLocAppModule::class,
        LocationWeatherDatabaseClientModule::class,
        LocationWeatherWebServiceClientModule::class,
        AndroidLocationUpdateProviderModule::class
    )
)
interface WeatherLocAppComponent {
    fun inject(target: MainActivity)
}
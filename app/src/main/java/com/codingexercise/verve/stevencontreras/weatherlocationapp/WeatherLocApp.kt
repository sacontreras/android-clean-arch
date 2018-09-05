package com.codingexercise.verve.stevencontreras.weatherlocationapp

import android.app.Application
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationprovider.android.di.AndroidLocationUpdateProviderModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.di.LocationWeatherDatabaseClientModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherprovider.webserviceclient.di.LocationWeatherWebServiceClientModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationUpdateProvider
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherHistoryRepository
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherProvider
import java.util.*
import javax.inject.Inject

class WeatherLocApp: Application() {

    private lateinit var weatherLocAppComponent: WeatherLocAppComponent

    @Inject
    protected lateinit var locationUpdateProvider: LocationUpdateProvider

    @Inject
    protected lateinit var locationWeatherHistoryRepository: LocationWeatherHistoryRepository

    @Inject
    protected lateinit var locationWeatherProvider: LocationWeatherProvider

    override fun onCreate() {
        super.onCreate()

        val properties = Properties()
        properties.load(applicationContext.assets.open("application.properties"))
        val apikey = properties.getProperty("lws.apikey") ?: ""

        weatherLocAppComponent = DaggerWeatherLocAppComponent.builder()
            .weatherLocAppModule(WeatherLocAppModule(this))
            .locationWeatherDatabaseClientModule(LocationWeatherDatabaseClientModule(applicationContext))
            .locationWeatherWebServiceClientModule(LocationWeatherWebServiceClientModule(apikey))
            .androidLocationUpdateProviderModule(AndroidLocationUpdateProviderModule(applicationContext))
            .build()
    }

    fun getWeatherLocAppComponent(): WeatherLocAppComponent {
        return weatherLocAppComponent
    }
}
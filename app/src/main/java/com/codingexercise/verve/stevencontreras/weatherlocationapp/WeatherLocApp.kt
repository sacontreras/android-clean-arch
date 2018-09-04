package com.codingexercise.verve.stevencontreras.weatherlocationapp

import android.app.Application
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.AppComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.AppModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.DaggerAppComponent

class WeatherLocApp: Application() {

    private lateinit var weatherLocAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        weatherLocAppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getWeatherLocAppComponent(): AppComponent {
        return weatherLocAppComponent
    }
}
package com.codingexercise.verve.stevencontreras.weatherlocationapp

import android.app.Application
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.components.AppComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.components.DaggerAppComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.modules.AppModule

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
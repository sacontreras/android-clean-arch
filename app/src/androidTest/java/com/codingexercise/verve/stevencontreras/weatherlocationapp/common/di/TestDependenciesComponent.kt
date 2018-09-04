package com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di

import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.di.LocationWeatherDatabaseClientModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherprovider.webserviceclient.di.LocationWeatherWebServiceClientModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ContextModule::class, LocationWeatherWebServiceClientModule::class, LocationWeatherDatabaseClientModule::class))
interface TestDependenciesComponent {
    fun inject(target: DependencyInjectionTarget)
}
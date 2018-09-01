package com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.components

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.module.ContextModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.module.LocationWeatherServiceModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.DependencyInjectionTarget
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ContextModule::class, LocationWeatherServiceModule::class))
interface TestDependenciesComponent {
    fun inject(target: DependencyInjectionTarget)
}
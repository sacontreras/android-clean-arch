package com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.components

import com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.DependencyInjectionTarget
import com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.modules.ContextModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.data.remote.restfulapi.LocationWeatherServiceInstrumentedTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ContextModule::class))
interface ContextComponent {
    fun inject(target: LocationWeatherServiceInstrumentedTest)
    fun inject(target: DependencyInjectionTarget)
}
package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.component

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.module.LocationWeatherServiceModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(LocationWeatherServiceModule::class))
interface LocationWeatherServiceComponent
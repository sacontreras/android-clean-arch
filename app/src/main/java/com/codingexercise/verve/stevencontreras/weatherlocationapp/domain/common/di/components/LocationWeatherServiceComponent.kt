package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.components

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.modules.LocationWeatherServiceModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(LocationWeatherServiceModule::class))
interface LocationWeatherServiceComponent
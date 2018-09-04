package com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di

import android.content.Context
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherHistoryRepository
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherProviderRepository
import javax.inject.Inject

class DependencyInjectionTarget {
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var locationWeatherProviderRepository: LocationWeatherProviderRepository

    @Inject
    lateinit var locationWeatherHistoryRepository: LocationWeatherHistoryRepository
}
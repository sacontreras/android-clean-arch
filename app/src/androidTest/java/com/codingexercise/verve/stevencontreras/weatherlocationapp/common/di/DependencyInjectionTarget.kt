package com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di

import android.content.Context
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherHistoryRepository
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherProvider
import javax.inject.Inject

class DependencyInjectionTarget {
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var locationWeatherProvider: LocationWeatherProvider

    @Inject
    lateinit var locationWeatherHistoryRepository: LocationWeatherHistoryRepository
}
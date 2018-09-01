package com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di

import android.content.Context
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi.LocationWeatherService
import javax.inject.Inject

class DependencyInjectionTarget {
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var locationWeatherService: LocationWeatherService
}
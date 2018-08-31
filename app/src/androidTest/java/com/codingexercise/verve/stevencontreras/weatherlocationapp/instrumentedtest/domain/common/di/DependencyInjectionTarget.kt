package com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di

import com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.components.ContextComponent
import javax.inject.Inject

class DependencyInjectionTarget {
    @Inject
    lateinit var testContextComponent: ContextComponent

//    @Inject
//    lateinit var testLocationWeatherServiceComponent: TestLocationWeatherServiceComponent
}
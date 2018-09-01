package com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di

import android.app.Application
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.module.AppModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.module.ContextModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.module.LocationWeatherServiceModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.components.DaggerTestDependenciesComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.components.TestDependenciesComponent
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DependencyInjectionTest {

    @Test
    fun testLocationWeatherServiceComponentProvided() {
        val testDependenciesComponent: TestDependenciesComponent = DaggerTestDependenciesComponent.builder()
            .contextModule(ContextModule(InstrumentationRegistry.getContext()))
            .locationWeatherServiceModule(LocationWeatherServiceModule())
            .build()
        assert(testDependenciesComponent != null)
    }

    @Test
    fun testDependenciesInjected() {
        val testDependenciesComponent: TestDependenciesComponent = DaggerTestDependenciesComponent.builder()
            .contextModule(ContextModule(InstrumentationRegistry.getContext()))
            .locationWeatherServiceModule(LocationWeatherServiceModule())
            .build()
        val testDependencyInjectionTarget = DependencyInjectionTarget()
        testDependenciesComponent.inject(testDependencyInjectionTarget)

        assert(testDependencyInjectionTarget.context != null)
        assert(testDependencyInjectionTarget.locationWeatherService != null)
    }
}
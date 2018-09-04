package com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.di.LocationWeatherDatabaseClientModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherprovider.webserviceclient.di.LocationWeatherWebServiceClientModule
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DependencyInjectionTest {

    @Test
    fun testLocationWeatherServiceComponentProvided() {
        val testDependenciesComponent: TestDependenciesComponent = DaggerTestDependenciesComponent.builder()
            .contextModule(ContextModule(InstrumentationRegistry.getTargetContext()))
            .locationWeatherWebServiceClientModule(LocationWeatherWebServiceClientModule())
            .locationWeatherDatabaseClientModule(LocationWeatherDatabaseClientModule(InstrumentationRegistry.getTargetContext()))
            .build()
        assert(testDependenciesComponent != null)
    }

    @Test
    fun testDependenciesInjected() {
        val testDependenciesComponent: TestDependenciesComponent = DaggerTestDependenciesComponent.builder()
            .contextModule(ContextModule(InstrumentationRegistry.getTargetContext()))
            .locationWeatherWebServiceClientModule(LocationWeatherWebServiceClientModule())
            .locationWeatherDatabaseClientModule(LocationWeatherDatabaseClientModule(InstrumentationRegistry.getTargetContext()))
            .build()
        val testDependencyInjectionTarget = DependencyInjectionTarget()
        testDependenciesComponent.inject(testDependencyInjectionTarget)

        assert(testDependencyInjectionTarget.context != null)
        Assert.assertNotNull(testDependencyInjectionTarget.locationWeatherProviderRepository)
        Assert.assertNotNull(testDependencyInjectionTarget.locationWeatherHistoryRepository)
    }
}
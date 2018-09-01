package com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.data.remote.restfulapi

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.module.ContextModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.module.LocationWeatherServiceModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi.model.response.LocationWeatherResponse
import com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.DependencyInjectionTarget
import com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.components.DaggerTestDependenciesComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.components.TestDependenciesComponent
import org.junit.Test
import org.junit.runner.RunWith
import org.modelmapper.ModelMapper
import java.util.*

@RunWith(AndroidJUnit4::class)
class LocationWeatherServiceInstrumentedTest {
    private var apikey: String
    private var lat: Double
    private var lon: Double
    private var city: String

    init {
        val context = InstrumentationRegistry.getTargetContext()
        val properties = Properties()
        properties.load(context.assets.open("application.properties"))

        apikey = properties.getProperty("lws.apikey")
        lat = properties.getProperty("lws.test.lat")?.toDouble() ?: 0.0
        lon = properties.getProperty("lws.test.lat")?.toDouble() ?: 0.0
        city = properties.getProperty("lws.test.city") ?: "Akrotiri"    //<-- interesting!! the city at lat: 0.0, lon: 0.0...
    }

    //this test is superfluous as we enforce this at build time - see gradle build script
//    @Test
//    fun testApiKeySet() {
//        assert(apikey != null && apikey.trim().compareTo("") != 0)
//        assert(apikey.compareTo("YOUR_LOCATION_WEATHER_SERVICE_API_KEY") != 0)
//    }

    @Test
    fun getCurrentLocationWeather() {
        //Arrange
        val testDependenciesComponent: TestDependenciesComponent = DaggerTestDependenciesComponent.builder()
            .contextModule(ContextModule(InstrumentationRegistry.getContext()))
            .locationWeatherServiceModule(LocationWeatherServiceModule())
            .build()
        val testDependencyInjectionTarget = DependencyInjectionTarget()
        testDependenciesComponent.inject(testDependencyInjectionTarget)
        val locationWeatherService = testDependencyInjectionTarget.locationWeatherService

        //Act
        //call synchronously, but in the app this should be done asynchronously via enqueue()
        val call_getCurrentLocationWeather = locationWeatherService.getCurrentLocationWeather(lat, lon, apikey)
        val response = call_getCurrentLocationWeather.execute()

        //Assert
        val locationWeather = ModelMapper().map(response.body(), LocationWeatherResponse::class.java)!! //this operator asserts non-null
        assert(locationWeather.name?.compareTo(city) == 0)
    }
}
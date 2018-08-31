package com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.data.remote.restfulapi

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi.LocationWeatherService
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class LocationWeatherServiceInstrumentedTest {
    private lateinit var apikey: String

    init {
        val context = InstrumentationRegistry.getTargetContext()
        val properties = Properties()
        properties.load(context.assets.open("application.properties"))
        apikey = properties.getProperty("lws.apikey")
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
        val lat = 33.34798; val lon = -117.23084


        //Act
//        val call_getCurrentLocationWeather = locationWeatherService.getCurrentLocationWeather(lat, lon, apikey)
//        val response = call_getCurrentLocationWeather.execute()
//        val locationWeather = response.body()
    }
}
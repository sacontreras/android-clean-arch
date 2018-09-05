package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherprovider

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.DependencyInjectionTarget
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.TestDependenciesComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.ContextModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.DaggerTestDependenciesComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.di.LocationWeatherDatabaseClientModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherprovider.webserviceclient.di.LocationWeatherWebServiceClientModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.history.AddNewLocationWeatherHistory
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.history.AddNewLocationWeatherHistoryResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.history.DeleteLocationWeatherHistory
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.history.DeleteLocationWeatherHistoryResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.GetCurrentLocationWeather
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.GetCurrentLocationWeatherResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.history.GetLocationWeatherHistory
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.history.GetLocationWeatherHistoryResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.Coord
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class LocationWeatherWebServiceClientIntegrationTest {
    private var apikey: String
    private var lat: Double
    private var lon: Double
    private var city: String

    init {
        val context = InstrumentationRegistry.getTargetContext()
        val properties = Properties()
        properties.load(context.assets.open("application.properties"))

        apikey = properties.getProperty("lws.apikey")
        val slat: String = properties.getProperty("lws.test.lat") ?: ""
        val slon: String = properties.getProperty("lws.test.lon") ?: ""
        city = properties.getProperty("lws.test.city")
        Log.d("LocationWeatherWebServiceClientIntegrationTest", String.format("lat '%s' lon '%s' city '%s'", slat, slon, city))

        if (slat.trim().isEmpty() || slon.trim().isEmpty() || city.trim().isEmpty()) {
            lat = 0.0
            lon = 0.0
            city = "Akrotiri"   //<-- interesting!! the city at lat: 0.0, lon: 0.0...
        } else {
            lat = slat.toDouble()
            lon = slon.toDouble()
        }
    }

    //this test is superfluous as we enforce this at build time - see gradle build script
//    @Test
//    fun testApiKeySet() {
//        assert(apikey != null && apikey.trim().compareTo("") != 0)
//        assert(apikey.compareTo("YOUR_LOCATION_WEATHER_SERVICE_API_KEY") != 0)
//    }

    @Test
    fun WebServiceIntegration() {
        //Arrange
        val testDependenciesComponent: TestDependenciesComponent = DaggerTestDependenciesComponent.builder()
            .contextModule(ContextModule(InstrumentationRegistry.getTargetContext()))
            .locationWeatherWebServiceClientModule(LocationWeatherWebServiceClientModule(apikey))
            .locationWeatherDatabaseClientModule(LocationWeatherDatabaseClientModule(InstrumentationRegistry.getTargetContext()))
            .build()
        val testDependencyInjectionTarget = DependencyInjectionTarget()
        testDependenciesComponent.inject(testDependencyInjectionTarget)

        //Act - get current weather info for given coords from web service
        val waitObject = Object()
        val getCurrentLocationWeather = GetCurrentLocationWeather(testDependencyInjectionTarget.locationWeatherProvider)
        val getCurrentLocationWeatherLocationWeatherResultObserver: GetCurrentLocationWeatherResultObserver?
        getCurrentLocationWeatherLocationWeatherResultObserver = GetCurrentLocationWeatherResultObserver(waitObject)
        getCurrentLocationWeather.execute(getCurrentLocationWeatherLocationWeatherResultObserver, Coord(lon, lat))
        getCurrentLocationWeatherLocationWeatherResultObserver.awaitCompletion(5000)

        //Assert - since we used coords that we know are from Fallbrook, CA, assert that city name of dto returned matches
        Assert.assertNotNull(getCurrentLocationWeatherLocationWeatherResultObserver.locationWeather)
        Assert.assertEquals(city, getCurrentLocationWeatherLocationWeatherResultObserver.locationWeather!!.name)

        //Act - insert LIVE data from getLocationWeatherResultObserver.locationWeather dto into database
        val addNewLocationWeather = AddNewLocationWeatherHistory(testDependencyInjectionTarget.locationWeatherHistoryRepository)
        var addNewLocationWeatherHistoryResultObserver: AddNewLocationWeatherHistoryResultObserver?
        addNewLocationWeatherHistoryResultObserver = AddNewLocationWeatherHistoryResultObserver(waitObject)
        addNewLocationWeather.execute(addNewLocationWeatherHistoryResultObserver, getCurrentLocationWeatherLocationWeatherResultObserver.locationWeather!!)
        addNewLocationWeatherHistoryResultObserver.awaitCompletion(5000)

        //Assert - assert matching results from addNewResult of observer
        Assert.assertNotNull(addNewLocationWeatherHistoryResultObserver.addNewResult)
//        Assert.assertEquals(1, addNewHistoryResultObserver.addNewResult.lwdid)    //we can't know this either since tests from db integration may be running concurrently
//        Assert.assertEquals(1, addNewHistoryResultObserver.addNewResult.wid.size) //we can't know this since this is LIVE data!
//        Assert.assertEquals(1, addNewHistoryResultObserver.addNewResult.wid[0])   //we can't know this since this is LIVE data!

        //Act - now retrieve from database based on the id returned by the insert operation
        val getLocationWeatherHistory = GetLocationWeatherHistory(testDependencyInjectionTarget.locationWeatherHistoryRepository)
        var getLocationWeatherAllLocationWeatherHistoryResultObserver: GetLocationWeatherHistoryResultObserver?
        getLocationWeatherAllLocationWeatherHistoryResultObserver = GetLocationWeatherHistoryResultObserver(waitObject)
        getLocationWeatherHistory.execute(getLocationWeatherAllLocationWeatherHistoryResultObserver, addNewLocationWeatherHistoryResultObserver.addNewResult.lwdid)
        getLocationWeatherAllLocationWeatherHistoryResultObserver.awaitCompletion(5000)

        //Assert - should match input DTO from the above insert (live data retrieved from web service)
        Assert.assertNotNull(getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList)
        Assert.assertEquals(1, getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList.size)
        Assert.assertEquals(getCurrentLocationWeatherLocationWeatherResultObserver.locationWeather, getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList[0])

        //Act - delete from database
        val deleteLocationWeatherHistory = DeleteLocationWeatherHistory(testDependencyInjectionTarget.locationWeatherHistoryRepository)
        var deleteLocationWeatherLocationWeatherHistoryResultObserver: DeleteLocationWeatherHistoryResultObserver?
        deleteLocationWeatherLocationWeatherHistoryResultObserver = DeleteLocationWeatherHistoryResultObserver(waitObject)
        deleteLocationWeatherHistory.execute(deleteLocationWeatherLocationWeatherHistoryResultObserver, addNewLocationWeatherHistoryResultObserver.addNewResult.lwdid) //the lwdid of the first insert
        deleteLocationWeatherLocationWeatherHistoryResultObserver.awaitCompletion(5000)

        //Act - now retrieve from database based on the id returned by the insert operation
        getLocationWeatherAllLocationWeatherHistoryResultObserver = GetLocationWeatherHistoryResultObserver(waitObject)
        getLocationWeatherHistory.execute(getLocationWeatherAllLocationWeatherHistoryResultObserver, addNewLocationWeatherHistoryResultObserver.addNewResult.lwdid)
        getLocationWeatherAllLocationWeatherHistoryResultObserver.awaitCompletion(5000)

        //Assert - should now be emmpty
        Assert.assertNotNull(getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList)
        Assert.assertEquals(0, getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList.size)

        getCurrentLocationWeather.dispose()
    }
}
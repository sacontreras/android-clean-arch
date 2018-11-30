package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.dao

import android.support.test.InstrumentationRegistry
import android.support.test.rule.provider.ProviderTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.ContextModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.DaggerTestDependenciesComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.DependencyInjectionTarget
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.TestDependenciesComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.mock.Util
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.LocationWeatherHistoryContentProvider
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.di.LocationWeatherDatabaseClientModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherprovider.webserviceclient.di.LocationWeatherWebServiceClientModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.history.*
import junit.framework.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class LocationWeatherHistoryContentProviderIntegrationTest {
    companion object {
        val TAG = LocationWeatherHistoryContentProviderIntegrationTest::class.java.simpleName
    }

    @get:Rule
    val locationWeatherHistoryProviderRule: ProviderTestRule = ProviderTestRule.Builder(LocationWeatherHistoryContentProvider::class.java, LocationWeatherHistoryContentProvider.AUTHORITY).build()

    @Test
    fun query() {
        /***************** first must insert - BEGIN *****************/
        //Arrange
        val properties = Properties()
        properties.load(InstrumentationRegistry.getTargetContext().assets.open("application.properties"))
        val apikey = properties.getProperty("lws.apikey") ?: ""
        val testDependenciesComponent: TestDependenciesComponent = DaggerTestDependenciesComponent.builder()
                .contextModule(ContextModule(InstrumentationRegistry.getTargetContext()))
                .locationWeatherWebServiceClientModule(LocationWeatherWebServiceClientModule(apikey))
                .locationWeatherDatabaseClientModule(LocationWeatherDatabaseClientModule(InstrumentationRegistry.getTargetContext()))
                .build()
        val testDependencyInjectionTarget = DependencyInjectionTarget()
        testDependenciesComponent.inject(testDependencyInjectionTarget)
        val waitObject = Object()
        val addNewLocationWeather = AddNewLocationWeatherHistory(testDependencyInjectionTarget.locationWeatherHistoryRepository)
        var addNewLocationWeatherHistoryResultObserver: AddNewLocationWeatherHistoryResultObserver?

        //Act - insert Util.Fixed.mockLW[0]
        addNewLocationWeatherHistoryResultObserver = AddNewLocationWeatherHistoryResultObserver(waitObject)
        addNewLocationWeather.execute(addNewLocationWeatherHistoryResultObserver, Util.Fixed.mockLW[0])
        addNewLocationWeatherHistoryResultObserver.awaitCompletion(5000)

        //Assert - assert matching results from addNewResult of observer
        Assert.assertNotNull(addNewLocationWeatherHistoryResultObserver.addNewResult)
        Assert.assertEquals(1, addNewLocationWeatherHistoryResultObserver.addNewResult.lwdid)
        Assert.assertEquals(1, addNewLocationWeatherHistoryResultObserver.addNewResult.wid.size)
        Assert.assertEquals(1, addNewLocationWeatherHistoryResultObserver.addNewResult.wid[0])

        //Act - insert Util.Fixed.mockLW[1]
        addNewLocationWeatherHistoryResultObserver = AddNewLocationWeatherHistoryResultObserver(waitObject)
        addNewLocationWeather.execute(addNewLocationWeatherHistoryResultObserver, Util.Fixed.mockLW[1])
        addNewLocationWeatherHistoryResultObserver.awaitCompletion(5000)

        //Assert - assert matching results from addNewResult of observer
        Assert.assertNotNull(addNewLocationWeatherHistoryResultObserver.addNewResult)
        Assert.assertEquals(2, addNewLocationWeatherHistoryResultObserver.addNewResult.lwdid)
        Assert.assertEquals(1, addNewLocationWeatherHistoryResultObserver.addNewResult.wid.size)
        Assert.assertEquals(2, addNewLocationWeatherHistoryResultObserver.addNewResult.wid[0])

        addNewLocationWeather.dispose()
        /***************** first must insert - END *****************/

        /***************** now must assert insert via get - BEGIN *****************/
        var getLocationWeatherHistory = GetLocationWeatherHistory(testDependencyInjectionTarget.locationWeatherHistoryRepository)
        var getLocationWeatherAllLocationWeatherHistoryResultObserver: GetLocationWeatherHistoryResultObserver?
        getLocationWeatherAllLocationWeatherHistoryResultObserver = GetLocationWeatherHistoryResultObserver(waitObject)
        getLocationWeatherHistory.execute(getLocationWeatherAllLocationWeatherHistoryResultObserver, null)
        getLocationWeatherAllLocationWeatherHistoryResultObserver.awaitCompletion(5000)

        //Assert - should match input DTOs from insert()
        Assert.assertNotNull(getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList)
        Assert.assertEquals(2, getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList.size)
        var now = System.currentTimeMillis()
        Util.Fixed.mockLW[0].timestamp = now
        Log.d(TAG, String.format("query: Util.Fixed.mockLW[0]: %s", Util.Fixed.mockLW[0]))
        getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList[0].timestamp = now
        Log.d(TAG, String.format("query: getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList[0]: %s", getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList[0]))
        Assert.assertEquals(Util.Fixed.mockLW[0], getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList[0])
        now = System.currentTimeMillis()
        Util.Fixed.mockLW[1].timestamp = now
        Log.d(TAG, String.format("query: Util.Fixed.mockLW[1]: %s", Util.Fixed.mockLW[1]))
        getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList[1].timestamp = now
        Log.d(TAG, String.format("query: getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList[1]: %s", getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList[1]))
        Assert.assertEquals(Util.Fixed.mockLW[1], getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList[1])

        getLocationWeatherHistory.dispose()
        /***************** now must assert insert via get - END *****************/

        /***************** now query content provider - BEGIN *****************/
        val locationWeatherHistoryContentResolver = locationWeatherHistoryProviderRule.resolver
        val cursor = locationWeatherHistoryContentResolver.query(LocationWeatherHistoryContentProvider.CONTENT_URI, null, null, null, null)
        Log.d(TAG, String.format("query: cursor from locationWeatherHistoryContentResolver.query() has %d records", cursor.count))
        if (cursor?.count!! > 0) {
            cursor.moveToFirst()

            for ((m, scol) in cursor.columnNames.withIndex()) {
                var tcol = cursor.getType(m)
                var ncol = cursor.getColumnName(m)
                Log.d(TAG, String.format("query: column %d - name: \"%s\", type: %s ", m, ncol, tcol))
            }

            var n = 0
            do {
                n++
            } while (cursor.moveToNext())
        }
        cursor.close()
        /***************** now query content provider - END *****************/

        /***************** finally must delete - BEGIN *****************/
        val deleteLocationWeatherHistory = DeleteLocationWeatherHistory(testDependencyInjectionTarget.locationWeatherHistoryRepository)
        var deleteLocationWeatherLocationWeatherHistoryResultObserver: DeleteLocationWeatherHistoryResultObserver?
        deleteLocationWeatherLocationWeatherHistoryResultObserver = DeleteLocationWeatherHistoryResultObserver(waitObject)
        deleteLocationWeatherHistory.execute(deleteLocationWeatherLocationWeatherHistoryResultObserver, 1) //the lwdid of the first insert
        deleteLocationWeatherLocationWeatherHistoryResultObserver.awaitCompletion(5000)

        //Act - delete second
        deleteLocationWeatherLocationWeatherHistoryResultObserver = DeleteLocationWeatherHistoryResultObserver(waitObject)
        deleteLocationWeatherHistory.execute(deleteLocationWeatherLocationWeatherHistoryResultObserver, 2) //the lwdid of the second insert
        deleteLocationWeatherLocationWeatherHistoryResultObserver.awaitCompletion(5000)

        //Act - get all records
        getLocationWeatherHistory = GetLocationWeatherHistory(testDependencyInjectionTarget.locationWeatherHistoryRepository)
        getLocationWeatherAllLocationWeatherHistoryResultObserver = GetLocationWeatherHistoryResultObserver(waitObject)
        getLocationWeatherHistory.execute(getLocationWeatherAllLocationWeatherHistoryResultObserver, null)
        getLocationWeatherAllLocationWeatherHistoryResultObserver.awaitCompletion(5000)

        //Assert - should now be emmpty
        Assert.assertNotNull(getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList)
        Assert.assertEquals(0, getLocationWeatherAllLocationWeatherHistoryResultObserver.resultList.size)

        deleteLocationWeatherHistory.dispose()
        getLocationWeatherHistory.dispose()
        /***************** finally must delete - END *****************/
    }
}
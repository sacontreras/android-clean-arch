package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.dao

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.ContextModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.DaggerTestDependenciesComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.DependencyInjectionTarget
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di.TestDependenciesComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.mock.Util
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.di.LocationWeatherDatabaseClientModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherprovider.webserviceclient.di.LocationWeatherWebServiceClientModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.addnewlocationweatherhistory.AddNewHistory
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.addnewlocationweatherhistory.AddNewHistoryResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.deletelocationweatherhistory.DeleteHistory
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.deletelocationweatherhistory.DeleteHistoryResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.getlocationweatherhistory.GetHistory
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.getlocationweatherhistory.GetHistoryResultObserver
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocationWeatherDbIntegrationTest {

    @Test
    fun insert() {
        //Arrange
        val testDependenciesComponent: TestDependenciesComponent = DaggerTestDependenciesComponent.builder()
            .contextModule(ContextModule(InstrumentationRegistry.getTargetContext()))
            .locationWeatherWebServiceClientModule(LocationWeatherWebServiceClientModule(""))
            .locationWeatherDatabaseClientModule(LocationWeatherDatabaseClientModule(InstrumentationRegistry.getTargetContext()))
            .build()
        val testDependencyInjectionTarget = DependencyInjectionTarget()
        testDependenciesComponent.inject(testDependencyInjectionTarget)
        val waitObject = Object()
        val addNewLocationWeather = AddNewHistory(testDependencyInjectionTarget.locationWeatherHistoryRepository)
        var addNewHistoryResultObserver: AddNewHistoryResultObserver?

        //Act - insert Util.Fixed.mockLW[0]
        addNewHistoryResultObserver = AddNewHistoryResultObserver(waitObject)
        addNewLocationWeather.execute(addNewHistoryResultObserver, Util.Fixed.mockLW[0])
        addNewHistoryResultObserver.awaitCompletion(5000)

        //Assert - assert matching results from addNewResult of observer
        Assert.assertNotNull(addNewHistoryResultObserver.addNewResult)
        Assert.assertEquals(1, addNewHistoryResultObserver.addNewResult.lwdid)
        Assert.assertEquals(1, addNewHistoryResultObserver.addNewResult.wid.size)
        Assert.assertEquals(1, addNewHistoryResultObserver.addNewResult.wid[0])

        //Act - insert Util.Fixed.mockLW[1]
        addNewHistoryResultObserver = AddNewHistoryResultObserver(waitObject)
        addNewLocationWeather.execute(addNewHistoryResultObserver, Util.Fixed.mockLW[1])
        addNewHistoryResultObserver.awaitCompletion(5000)

        //Assert - assert matching results from addNewResult of observer
        Assert.assertNotNull(addNewHistoryResultObserver.addNewResult)
        Assert.assertEquals(2, addNewHistoryResultObserver.addNewResult.lwdid)
        Assert.assertEquals(1, addNewHistoryResultObserver.addNewResult.wid.size)
        Assert.assertEquals(2, addNewHistoryResultObserver.addNewResult.wid[0])

        addNewLocationWeather.dispose()
    }

    @Test
    fun get() {
        //Arrange
        val testDependenciesComponent: TestDependenciesComponent = DaggerTestDependenciesComponent.builder()
            .contextModule(ContextModule(InstrumentationRegistry.getTargetContext()))
            .locationWeatherWebServiceClientModule(LocationWeatherWebServiceClientModule(""))
            .locationWeatherDatabaseClientModule(LocationWeatherDatabaseClientModule(InstrumentationRegistry.getTargetContext()))
            .build()
        val testDependencyInjectionTarget = DependencyInjectionTarget()
        testDependenciesComponent.inject(testDependencyInjectionTarget)

        //Act - get all records
        val waitObject = Object()
        val getLocationWeatherHistory = GetHistory(testDependencyInjectionTarget.locationWeatherHistoryRepository)
        val getLocationWeatherAllHistoryResultObserver: GetHistoryResultObserver?
        getLocationWeatherAllHistoryResultObserver = GetHistoryResultObserver(waitObject)
        getLocationWeatherHistory.execute(getLocationWeatherAllHistoryResultObserver, null)
        getLocationWeatherAllHistoryResultObserver.awaitCompletion(5000)

        //Assert - should match input DTOs from insert()
        Assert.assertNotNull(getLocationWeatherAllHistoryResultObserver.resultList)
        Assert.assertEquals(2, getLocationWeatherAllHistoryResultObserver.resultList.size)
        Assert.assertEquals(Util.Fixed.mockLW[0], getLocationWeatherAllHistoryResultObserver.resultList[0])
        Assert.assertEquals(Util.Fixed.mockLW[1], getLocationWeatherAllHistoryResultObserver.resultList[1])

        getLocationWeatherHistory.dispose()
    }

    @Test
    fun delete() {
        //Arrange
        val testDependenciesComponent: TestDependenciesComponent = DaggerTestDependenciesComponent.builder()
            .contextModule(ContextModule(InstrumentationRegistry.getTargetContext()))
            .locationWeatherWebServiceClientModule(LocationWeatherWebServiceClientModule(""))
            .locationWeatherDatabaseClientModule(LocationWeatherDatabaseClientModule(InstrumentationRegistry.getTargetContext()))
            .build()
        val testDependencyInjectionTarget = DependencyInjectionTarget()
        testDependenciesComponent.inject(testDependencyInjectionTarget)

        //Act - delete first
        val waitObject = Object()
        val deleteLocationWeatherHistory = DeleteHistory(testDependencyInjectionTarget.locationWeatherHistoryRepository)
        var deleteLocationWeatherHistoryResultObserver: DeleteHistoryResultObserver?
        deleteLocationWeatherHistoryResultObserver = DeleteHistoryResultObserver(waitObject)
        deleteLocationWeatherHistory.execute(deleteLocationWeatherHistoryResultObserver, 1) //the lwdid of the first insert
        deleteLocationWeatherHistoryResultObserver.awaitCompletion(5000)

        //Act - delete second
        deleteLocationWeatherHistoryResultObserver = DeleteHistoryResultObserver(waitObject)
        deleteLocationWeatherHistory.execute(deleteLocationWeatherHistoryResultObserver, 2) //the lwdid of the second insert
        deleteLocationWeatherHistoryResultObserver.awaitCompletion(5000)

        //Act - get all records
        val getLocationWeatherHistory = GetHistory(testDependencyInjectionTarget.locationWeatherHistoryRepository)
        val getLocationWeatherAllHistoryResultObserver: GetHistoryResultObserver?
        getLocationWeatherAllHistoryResultObserver = GetHistoryResultObserver(waitObject)
        getLocationWeatherHistory.execute(getLocationWeatherAllHistoryResultObserver, null)
        getLocationWeatherAllHistoryResultObserver.awaitCompletion(5000)

        //Assert - should now be emmpty
        Assert.assertNotNull(getLocationWeatherAllHistoryResultObserver.resultList)
        Assert.assertEquals(0, getLocationWeatherAllHistoryResultObserver.resultList.size)

        deleteLocationWeatherHistory.dispose()
        getLocationWeatherHistory.dispose()
    }
}
package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model

import android.support.test.runner.AndroidJUnit4
import com.codingexercise.verve.stevencontreras.weatherlocationapp.common.mock.Util
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.model.LocationWeatherDetailsEntity
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.modelmapper.ModelMapper

@RunWith(AndroidJUnit4::class)
class LocationWeatherDTOModelMapTest {

    @Test
    fun newDTOEmptyCtorMapToLocationWeatherDetailsEntity() {
        //Arrange
        val modelMapper = ModelMapper()
        val locationWeatherDTO = LocationWeather()

        //Act
        val locationWeatherDetailsEntity = modelMapper.map(locationWeatherDTO, LocationWeatherDetailsEntity::class.java)

        //Assert
        assertEquals(null, locationWeatherDetailsEntity.lwdId)
        assertEquals(null, locationWeatherDetailsEntity.coord)
        assertEquals(null, locationWeatherDetailsEntity.base)
        assertEquals(null, locationWeatherDetailsEntity.main)
        assertEquals(null, locationWeatherDetailsEntity.visibility)
        assertEquals(null, locationWeatherDetailsEntity.wind)
        assertEquals(null, locationWeatherDetailsEntity.clouds)
        assertEquals(null, locationWeatherDetailsEntity.dt)
        assertEquals(null, locationWeatherDetailsEntity.sys)
        assertEquals(null, locationWeatherDetailsEntity.id)
        assertEquals(null, locationWeatherDetailsEntity.name)
        assertEquals(null, locationWeatherDetailsEntity.cod)
    }

    @Test
    fun DTOWithAllMemberEmptyCtorMapToLocationWeatherDetailsEntity() {
        //Arrange
        val modelMapper = ModelMapper()
        val locationWeatherDTO = LocationWeather()
        locationWeatherDTO.coord = Coord()
        locationWeatherDTO.main = Main()
        locationWeatherDTO.wind = Wind()
        locationWeatherDTO.clouds = Clouds()
        locationWeatherDTO.sys = Sys()

        //Act
        val locationWeatherDetailsEntity = modelMapper.map(locationWeatherDTO, LocationWeatherDetailsEntity::class.java)

        //Assert
        assertEquals(null, locationWeatherDetailsEntity.coord?.lat)
        assertEquals(null, locationWeatherDetailsEntity.coord?.lon)
        //assertEquals(null, locationWeatherDetailsEntity.base) //already tested above
        assertEquals(null, locationWeatherDetailsEntity.main?.temp)
        assertEquals(null, locationWeatherDetailsEntity.main?.pressure)
        assertEquals(null, locationWeatherDetailsEntity.main?.humidity)
        assertEquals(null, locationWeatherDetailsEntity.main?.temp_min)
        assertEquals(null, locationWeatherDetailsEntity.main?.temp_max)
        //assertEquals(null, locationWeatherDetailsEntity.visibility)   //already tested above
        assertEquals(null, locationWeatherDetailsEntity.wind?.speed)
        assertEquals(null, locationWeatherDetailsEntity.wind?.deg)
        assertEquals(null, locationWeatherDetailsEntity.clouds?.all)
        //assertEquals(null, locationWeatherDetailsEntity.dt)   //already tested above
        assertEquals(null, locationWeatherDetailsEntity.sys?.type)
        assertEquals(null, locationWeatherDetailsEntity.sys?.id)
        assertEquals(null, locationWeatherDetailsEntity.sys?.message)
        assertEquals(null, locationWeatherDetailsEntity.sys?.country)
        assertEquals(null, locationWeatherDetailsEntity.sys?.sunrise)
        assertEquals(null, locationWeatherDetailsEntity.sys?.sunset)
        //assertEquals(null, locationWeatherDetailsEntity.id)   //already tested above
        //assertEquals(null, locationWeatherDetailsEntity.name) //already tested above
        //assertEquals(null, locationWeatherDetailsEntity.cod)  //already tested above
    }

    @Test
    fun DTOWithMemberMockVals() {
        //Arrange
        val modelMapper = ModelMapper()
        val locationWeatherDTO = LocationWeather()
        locationWeatherDTO.coord = Coord((1).toDouble(), (2).toDouble())
        locationWeatherDTO.base = "abcd"
        locationWeatherDTO.main = Main((1).toDouble(), (2).toDouble(), 3, (4).toDouble(), (5).toDouble())
        locationWeatherDTO.visibility = (100).toDouble()
        locationWeatherDTO.wind = Wind((1).toDouble(), (2).toDouble())
        locationWeatherDTO.clouds = Clouds(1)
        locationWeatherDTO.dt = 1
        locationWeatherDTO.sys = Sys((1).toDouble(), 2, (3).toDouble(), "US", 4, 5)
        locationWeatherDTO.id = 1
        locationWeatherDTO.name = "abcd"
        locationWeatherDTO.cod = 1

        //Act
        val locationWeatherDetailsEntity = modelMapper.map(locationWeatherDTO, LocationWeatherDetailsEntity::class.java)

        //Assert
        assertEquals((1).toDouble(), locationWeatherDetailsEntity.coord?.lon)
        assertEquals((2).toDouble(), locationWeatherDetailsEntity.coord?.lat)
        assertEquals("abcd", locationWeatherDetailsEntity.base)
        assertEquals((1).toDouble(), locationWeatherDetailsEntity.main?.temp)
        assertEquals((2).toDouble(), locationWeatherDetailsEntity.main?.pressure)
        assertEquals(3, locationWeatherDetailsEntity.main?.humidity)
        assertEquals((4).toDouble(), locationWeatherDetailsEntity.main?.temp_min)
        assertEquals((5).toDouble(), locationWeatherDetailsEntity.main?.temp_max)
        assertEquals((100).toDouble(), locationWeatherDetailsEntity.visibility)
        assertEquals((1).toDouble(), locationWeatherDetailsEntity.wind?.speed)
        assertEquals((2).toDouble(), locationWeatherDetailsEntity.wind?.deg)
        assertEquals(1, locationWeatherDetailsEntity.clouds?.all)
        assertEquals(1, locationWeatherDetailsEntity.dt)
        assertEquals((1).toDouble(), locationWeatherDetailsEntity.sys?.type)
        assertEquals(2, locationWeatherDetailsEntity.sys?.id)
        assertEquals((3).toDouble(), locationWeatherDetailsEntity.sys?.message)
        assertEquals("US", locationWeatherDetailsEntity.sys?.country)
        assertEquals(4, locationWeatherDetailsEntity.sys?.sunrise)
        assertEquals(5, locationWeatherDetailsEntity.sys?.sunset)
        assertEquals(1, locationWeatherDetailsEntity.id)
        assertEquals("abcd", locationWeatherDetailsEntity.name)
        assertEquals(1, locationWeatherDetailsEntity.cod)
    }

    @Test
    fun DTOFromJSONToEntity() {
        val locationWeather = Util.Fixed.mockLW[0]
        val locationWeatherDetails = ModelMapper().map(locationWeather, LocationWeatherDetailsEntity::class.java)
        Assert.assertEquals(locationWeather.name, locationWeatherDetails.name)
    }
}
package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi.model.LocationWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationWeatherService {
    @GET("/data/2.5/weather")
    fun getCurrentLocationWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("APPID") appId: String
    ): Call<LocationWeather>
}
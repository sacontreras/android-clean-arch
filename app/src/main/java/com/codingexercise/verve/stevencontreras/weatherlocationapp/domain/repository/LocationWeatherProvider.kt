package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.weather.LocationWeather
import io.reactivex.Observable

interface LocationWeatherProvider {
    fun get(lat: Double, lon: Double): Observable<LocationWeather>
}
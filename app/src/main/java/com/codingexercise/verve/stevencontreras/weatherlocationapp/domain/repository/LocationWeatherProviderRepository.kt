package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.LocationWeather
import io.reactivex.Observable

interface LocationWeatherProviderRepository {
    fun get(lat: Double, lon: Double): Observable<LocationWeather>
}
package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event.LocationEvent
import io.reactivex.Observable

interface LocationUpdateProvider {
    fun startUpdates(): Observable<LocationEvent>
    fun stopUpdates(): Observable<LocationEvent>
}
package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.weather.LocationWeather
import io.reactivex.Observable

data class AddNewResult(
    val lwdid: Long,
    val wid: List<Long>
)

open class LocationWeatherHistoryRepositoryException(private val msg: String, override val cause: Throwable): RuntimeException(msg)
class GetException(private val msg: String, override val cause: Throwable): LocationWeatherHistoryRepositoryException(msg, cause)
class AddNewException(private val msg: String, override val cause: Throwable): LocationWeatherHistoryRepositoryException(msg, cause)
class DeleteException(private val msg: String, override val cause: Throwable): LocationWeatherHistoryRepositoryException(msg, cause)

interface LocationWeatherHistoryRepository {
    fun get(lwdid: Long?): Observable<List<LocationWeather>>
    fun addNew(locationWeather: LocationWeather): Observable<AddNewResult>
    fun delete(lwdid: Long): Observable<Boolean>
}
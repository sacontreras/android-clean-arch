package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.ReactiveUseCase
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.Coord
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.weather.LocationWeather
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherProvider
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetCurrentLocationWeather @Inject constructor(
    val locationWeatherProvider: LocationWeatherProvider,
    override val requestSchedulerExecutor: Executor,
    override val resultThreadScheduler: Scheduler
): ReactiveUseCase<Coord, LocationWeather>(requestSchedulerExecutor, resultThreadScheduler) {

    //default threadpool executor cfg for request scheduling and single-threaded scheduler for result-handling
    constructor(locationWeatherProvider: LocationWeatherProvider): this(
        locationWeatherProvider,
        ThreadPoolExecutor(
            1,
            1,
            5,
            TimeUnit.SECONDS,
            ArrayBlockingQueue(5)
        ),
        Schedulers.single()
    )

    override fun newObservableResult(request: Coord): Observable<LocationWeather> {
        return locationWeatherProvider.get(request.lon!!, request.lat!!)  //"!!" is short-hand for null-check and will throw exception is true
    }
}
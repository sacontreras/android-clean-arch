package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.getcurrentlocationweather

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.ReactiveUseCase
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.Coord
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.LocationWeather
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherHistoryRepository
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherProviderRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetCurrent @Inject constructor(
        val locationWeatherProviderRepository: LocationWeatherProviderRepository,
        override val requestSchedulerExecutor: Executor,
        override val resultThreadScheduler: Scheduler
): ReactiveUseCase<Coord, LocationWeather>(requestSchedulerExecutor, resultThreadScheduler) {

    //default threadpool executor cfg for request scheduling and single-threaded scheduler for result-handling
    constructor(locationWeatherProviderRepository: LocationWeatherProviderRepository): this(
        locationWeatherProviderRepository,
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
        return locationWeatherProviderRepository.get(request.lon!!, request.lat!!)  //"!!" is short-hand for null-check and will throw exception is true
    }
}
package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.history

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.ReactiveUseCase
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherHistoryRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DeleteLocationWeatherHistory @Inject constructor(
    private val locationWeatherHistoryRepository: LocationWeatherHistoryRepository,
    override val requestSchedulerExecutor: Executor,
    override val resultThreadScheduler: Scheduler
): ReactiveUseCase<Long, Boolean>(requestSchedulerExecutor, resultThreadScheduler) {

    //default threadpool executor cfg for request scheduling and single-threaded scheduler for result-handling
    constructor(locationWeatherHistoryRepository: LocationWeatherHistoryRepository): this(
        locationWeatherHistoryRepository,
        ThreadPoolExecutor(
            1,
            1,
            5,
            TimeUnit.SECONDS,
            ArrayBlockingQueue(5)
        ),
        Schedulers.single()
    )

    override fun newObservableResult(request: Long): Observable<Boolean> {
        return locationWeatherHistoryRepository.delete(request)
    }
}
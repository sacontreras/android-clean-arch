package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.updates

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.ReactiveUseCase
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.Coord
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event.LocationEvent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationUpdateProvider
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StartLocationUpdates @Inject constructor(
    private val locationUpdateProvider: LocationUpdateProvider,
    override val requestSchedulerExecutor: Executor,
    override val resultThreadScheduler: Scheduler
): ReactiveUseCase<Void?, LocationEvent>(requestSchedulerExecutor, resultThreadScheduler) {

    //default threadpool executor cfg for request scheduling and single-threaded scheduler for result-handling
    constructor(locationUpdateProvider: LocationUpdateProvider): this(
        locationUpdateProvider,
        ThreadPoolExecutor(
            1,
            1,
            5,
            TimeUnit.SECONDS,
            ArrayBlockingQueue(5)
        ),
        Schedulers.single()
    )

    override fun newObservableResult(request: Void?): Observable<LocationEvent> {
        return locationUpdateProvider.startUpdates()
    }
}
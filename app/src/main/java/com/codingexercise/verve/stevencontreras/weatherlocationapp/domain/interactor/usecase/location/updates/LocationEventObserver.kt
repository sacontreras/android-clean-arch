package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.updates

import android.util.Log
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.ReactiveUseCaseResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event.LocationEvent

open class LocationEventObserver(override val waitObj: Object? = null): ReactiveUseCaseResultObserver<LocationEvent>(waitObj) {
    constructor(): this(waitObj = Object())
    companion object {
        val TAG = LocationEventObserver::class.java.simpleName
    }

    override fun onComplete() {
        Log.d(TAG, String.format("nothing to see here"))
        super.onComplete()
    }

    override fun onNext(t: LocationEvent) {
        Log.d(TAG, String.format("locationEvent: %s", t.toString()))
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        super.onError(e)
    }
}
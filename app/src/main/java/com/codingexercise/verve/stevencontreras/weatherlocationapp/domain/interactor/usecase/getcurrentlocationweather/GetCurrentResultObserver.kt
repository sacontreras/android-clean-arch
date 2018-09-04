package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.getcurrentlocationweather

import android.util.Log
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.ReactiveUseCaseResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.LocationWeather

class GetCurrentResultObserver(override val waitObj: Object? = null): ReactiveUseCaseResultObserver<LocationWeather>(waitObj) {
    companion object {
        val TAG = GetCurrentResultObserver::class.java.simpleName
    }

    var locationWeather: LocationWeather? = null

    override fun onComplete() {
        Log.d(TAG, String.format("nothing to see here"))
        super.onComplete()
    }

    override fun onNext(t: LocationWeather) {
        Log.d(TAG, String.format("locationWeather: %s", t.toString()))
        locationWeather = t
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        super.onError(e)
    }
}
package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather

import android.util.Log
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.ReactiveUseCaseResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.weather.LocationWeather

open class GetCurrentLocationWeatherResultObserver(override val waitObj: Object? = null): ReactiveUseCaseResultObserver<LocationWeather>(waitObj) {
    constructor(): this(waitObj = Object())
    companion object {
        val TAG = GetCurrentLocationWeatherResultObserver::class.java.simpleName
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
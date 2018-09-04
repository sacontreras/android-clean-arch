package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.getlocationweatherhistory

import android.util.Log
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.ReactiveUseCaseResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.LocationWeather

class GetHistoryResultObserver(override val waitObj: Object? = null): ReactiveUseCaseResultObserver<List<LocationWeather>>(waitObj) {
    companion object {
        val TAG = GetHistoryResultObserver::class.java.simpleName
    }

    var resultList: List<LocationWeather> = ArrayList()

    override fun onComplete() {
        Log.d(TAG, String.format("nothing to see here"))
        super.onComplete()
    }

    override fun onNext(t: List<LocationWeather>) {
        Log.d(TAG, String.format("record-count: %d", t.size))
        resultList = t
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        super.onError(e)
    }
}
package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.deletelocationweatherhistory

import android.util.Log
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.ReactiveUseCaseResultObserver

class DeleteHistoryResultObserver(override val waitObj: Object? = null): ReactiveUseCaseResultObserver<Boolean>(waitObj) {
    companion object {
        val TAG = DeleteHistoryResultObserver::class.java.simpleName
    }

    var deleted: Boolean = false

    override fun onComplete() {
        Log.d(TAG, String.format("nothing to see here"))
        super.onComplete()
    }

    override fun onNext(t: Boolean) {
        Log.d(TAG, String.format("deleted: %b", t))
        deleted = t
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        super.onError(e)
    }
}
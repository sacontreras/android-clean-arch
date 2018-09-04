package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.addnewlocationweatherhistory

import android.util.Log
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.ReactiveUseCaseResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.AddNewResult

class AddNewHistoryResultObserver(override val waitObj: Object? = null): ReactiveUseCaseResultObserver<AddNewResult>(waitObj) {
    companion object {
        val TAG = AddNewHistoryResultObserver::class.java.simpleName
    }

    lateinit var addNewResult: AddNewResult

    override fun onComplete() {
        Log.d(TAG, String.format("nothing to see here"))
        super.onComplete()
    }

    override fun onNext(t: AddNewResult) {
        Log.d(TAG, String.format("AddNewHistory succeeded: %s", t.toString()))
        addNewResult = t
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        super.onError(e)
    }
}
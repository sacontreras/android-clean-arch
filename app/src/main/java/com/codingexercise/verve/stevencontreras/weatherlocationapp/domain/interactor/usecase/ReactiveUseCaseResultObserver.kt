package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase

import io.reactivex.observers.DisposableObserver

open class ReactiveUseCaseResultObserver<TResult>(
    open val waitObj: Object? = null
): DisposableObserver<TResult>() {
    constructor(): this(waitObj = Object())

    override fun onComplete() { //if overridden, call super AFTER your impl in order to signal
        synchronized(waitObj!!) {
            waitObj!!.notifyAll()
        }
    }

    override fun onNext(t: TResult) {
        //no-op
    }

    override fun onError(e: Throwable) {    //if overridden, call super AFTER your impl in order to signal
        synchronized(waitObj!!) {
            waitObj!!.notifyAll()
        }
    }

    fun awaitCompletion(timeout: Long) {
        synchronized(waitObj!!) {
            waitObj!!.wait(timeout)
        }
    }
    fun awaitCompletion() {
        synchronized(waitObj!!) {
            waitObj!!.wait()
        }
    }
}
package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor

//an Interactor in Clean Architecure is based on the Command design pattern
//  but our execute paradigm is done in the reactive way - i.e. asynchronously, using observable/observer (publisher/subscriber pattern) streams
abstract class ReactiveUseCase<TRequest, TResult>(
    open val requestSchedulerExecutor: Executor,
    open val resultThreadScheduler: Scheduler
) {
    private val compositeDisposables = CompositeDisposable()    //per docs: A disposable container that can hold onto multiple other disposables and offers O(1) add and removal complexity

    abstract fun newObservableResult(request: TRequest): Observable<TResult>

    fun execute(useCaseResultObserver: ReactiveUseCaseResultObserver<TResult>, request: TRequest) {
        val observableResult = newObservableResult(request)
            .subscribeOn(Schedulers.from(requestSchedulerExecutor))     //requestSchedulerExecutor provides the threading model/scheme for a NEW scheduler used to produce the thread on which the request is to be executed
            .observeOn(resultThreadScheduler)                           //scheduler used to provide a thread on which the result is to be handled

        //"inform" observable result that useCaseResultObserver will be... observing it
        val disposableObserver = observableResult.subscribeWith(useCaseResultObserver)

        //add it to the disposables-container so that it can be cleaned up in O(1) time
        compositeDisposables.add(disposableObserver)
    }

    fun dispose() {
        if (!compositeDisposables.isDisposed)
            compositeDisposables.dispose()
    }
}
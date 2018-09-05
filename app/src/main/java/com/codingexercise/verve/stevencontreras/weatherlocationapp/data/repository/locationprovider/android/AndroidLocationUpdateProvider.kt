package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationprovider.android

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event.Event
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event.LocationEvent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationUpdateProvider
import io.reactivex.Observable
import javax.inject.Inject

class AndroidLocationUpdateProvider @Inject constructor(private val context: Context): LocationUpdateProvider {
    private var serviceConnection: ServiceConnection? = null
    private var fgsBinder: FGSBinder? = null

    override fun startUpdates(): Observable<LocationEvent> {
        val observableLocationEvent = Observable.create<LocationEvent> { emitter ->
            emitter.onNext(LocationEvent(event = Event.SERVICE_STARTING))
            try {
                serviceConnection = object: ServiceConnection {
                    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                        fgsBinder = service as FGSBinder
                        fgsBinder!!.getService().observabLocationEventEmitter = emitter
                        emitter.onNext(LocationEvent(event = Event.SERVICE_CONNECTED))
                        fgsBinder!!.getService().start_explicit()
                    }

                    override fun onServiceDisconnected(name: ComponentName?) {
                        emitter.onNext(LocationEvent(event = Event.SERVICE_DISCONNECTED))
                    }
                }
                context.bindService(
                    Intent(context, FGS::class.java),
                    serviceConnection,
                    Context.BIND_AUTO_CREATE
                )
            } catch (e: Exception) {
                e.printStackTrace()
                emitter.onNext(LocationEvent(event = Event.SERVICE_STOPPED_UNEXPECTEDLY, exception = e))
                emitter.onError(e)
            }
        }
        return observableLocationEvent
    }

    override fun stopUpdates(): Observable<LocationEvent> {
        val observableLocationEvent = Observable.create<LocationEvent> { emitter ->
            if (fgsBinder != null) {
                emitter.onNext(LocationEvent(event = Event.SERVICE_STOPPING))
                fgsBinder!!.getService().observabLocationEventEmitter = null
                fgsBinder!!.getService().stop_explicit()
                emitter.onNext(LocationEvent(event = Event.SERVICE_DISCONNECTING))
                context.unbindService(serviceConnection)
            } else
                emitter.onNext(LocationEvent(event = Event.SERVICE_DISCONNECTED))
            emitter.onComplete()
        }
        return observableLocationEvent
    }
}
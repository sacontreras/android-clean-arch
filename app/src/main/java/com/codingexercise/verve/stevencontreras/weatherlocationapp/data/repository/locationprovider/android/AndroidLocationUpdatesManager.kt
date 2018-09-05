package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationprovider.android

import android.Manifest
import android.app.Service
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.Coord
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event.Event
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event.LocationEvent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event.PlatformProviderStatus
import com.codingexercise.verve.stevencontreras.weatherlocationapp.presentation.common.util.Permissions
import io.reactivex.ObservableEmitter

class AndroidLocationUpdatesManager(private val context: Context, private val observabLocationEventEmitter: ObservableEmitter<LocationEvent>) {
    companion object {
        private val TAG = AndroidLocationUpdatesManager::class.java.canonicalName

        private val UPDATE_TRIGGER__DISTANCE_DELTA: Long = 1 //in meters
        private val UPDATE_TRIGGER__TIME_DELTA: Long = 500  //in milliseconds
    }

    private val androidLocationListener: LocationListener = object: LocationListener {
        override fun onLocationChanged(location: Location?) {
            observabLocationEventEmitter.onNext(LocationEvent(Event.LOCATION_CHANGED, Coord(location!!.longitude, location.latitude)))
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            observabLocationEventEmitter.onNext(
                LocationEvent(
                    event = Event.LOCATION_PLATFORM_PROVIDER_STATUS_CHANGED,
                    platformproviderstatus = PlatformProviderStatus(
                        platformprovider = provider,
                        code = status
                    )
                )
            )
        }

        override fun onProviderEnabled(provider: String?) {
            observabLocationEventEmitter.onNext(
                LocationEvent(
                    event = Event.LOCATION_PLATFORM_PROVIDER_ENABLED,
                    platformproviderstatus = PlatformProviderStatus(platformprovider = provider)
                )
            )
        }

        override fun onProviderDisabled(provider: String?) {
            observabLocationEventEmitter.onNext(
                LocationEvent(
                    event = Event.LOCATION_PLATFORM_PROVIDER_DISABLED,
                    platformproviderstatus = PlatformProviderStatus(platformprovider = provider)
                )
            )
        }
    }

    private val requiredPermissions = ArrayList<String>()
    private var gpsenabled = false
    private var networkenabled = false

    protected lateinit var systemLocationManager: LocationManager

    init {
        systemLocationManager = context.getSystemService(Service.LOCATION_SERVICE) as LocationManager
        gpsenabled = systemLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        networkenabled = systemLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    fun start_updates(): Boolean {
        requiredPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        requiredPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        val missingPermissions = Permissions.collateMissingPermissions(requiredPermissions, context)

        if (!(gpsenabled || networkenabled)) {
            Log.d(TAG, "start_updates: no GPS or network - location updates currently not possible")
            observabLocationEventEmitter.onNext(LocationEvent(event = Event.NO_GPS_OR_NETWORK))
            getLastLocation()
            return false
        } else {
            Log.d(TAG, "start_updates: network/gps enabled - location updates are possible")
            if (missingPermissions.size > 0) {
                val locationEvent = LocationEvent(event = Event.MISSING_PERRMISSIONS)
                locationEvent.permissions = ArrayList<String>()
                for (missingPermission in missingPermissions)
                    (locationEvent.permissions as ArrayList<String>).add(missingPermission)
                observabLocationEventEmitter.onNext(locationEvent)
                return false
            } else
                getLocation()
        }
        return true
    }

    fun stop_updates() {
        systemLocationManager.removeUpdates(androidLocationListener)
    }

    private fun getLastLocation() {
        try {
            val criteria = Criteria()
            val provider = systemLocationManager.getBestProvider(criteria, false)
            val location = systemLocationManager.getLastKnownLocation(provider)
            Log.d(TAG, String.format("getLastLocation: best location provider is: %s", provider))
            Log.d(TAG, String.format("getLastLocation: last known location: %s", location?.toString() ?: "null"))
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun getLocation() {
        val loc: Location
        try {
            if (gpsenabled) {
                // from GPS
                Log.d(TAG, "getLocation: via gps provider")
                systemLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    UPDATE_TRIGGER__TIME_DELTA,
                    UPDATE_TRIGGER__DISTANCE_DELTA.toFloat(),
                    androidLocationListener
                )

//                loc = systemLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//                if (loc != null)
//                    androidLocationListener.onLocationChanged(loc)
            } else if (networkenabled) {
                // from Network Provider
                Log.d(TAG, "NETWORK_PROVIDER on")
                systemLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    UPDATE_TRIGGER__TIME_DELTA,
                    UPDATE_TRIGGER__DISTANCE_DELTA.toFloat(),
                    androidLocationListener
                )

//                loc = systemLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
//                if (loc != null)
//                    androidLocationListener.onLocationChanged(loc)
            } else {
                androidLocationListener.onProviderDisabled(LocationManager.GPS_PROVIDER)
                androidLocationListener.onProviderDisabled(LocationManager.NETWORK_PROVIDER)
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}
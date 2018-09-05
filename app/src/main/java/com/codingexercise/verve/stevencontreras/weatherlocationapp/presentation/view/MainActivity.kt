package com.codingexercise.verve.stevencontreras.weatherlocationapp.presentation.view

import android.annotation.TargetApi
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.codingexercise.verve.stevencontreras.weatherlocationapp.R
import com.codingexercise.verve.stevencontreras.weatherlocationapp.WeatherLocApp
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.updates.LocationEventObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.updates.StartLocationUpdates
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.GetCurrentLocationWeather
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.GetCurrentLocationWeatherResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.history.AddNewLocationWeatherHistory
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.history.AddNewLocationWeatherHistoryResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.history.GetLocationWeatherHistory
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.history.GetLocationWeatherHistoryResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event.Event
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event.LocationEvent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.weather.LocationWeather
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.AddNewResult
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationUpdateProvider
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherHistoryRepository
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherProvider
import com.codingexercise.verve.stevencontreras.weatherlocationapp.presentation.common.util.Alerts
import com.codingexercise.verve.stevencontreras.weatherlocationapp.presentation.common.util.Permissions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val ALL_PERMISSIONS_RESULT = 101
    }

    @Inject
    protected lateinit var locationUpdateProvider: LocationUpdateProvider
    @Inject
    protected lateinit var locationWeatherProvider: LocationWeatherProvider
    @Inject
    protected lateinit var locationWeatherHistoryRepository: LocationWeatherHistoryRepository

    private var missingPermissions = ArrayList<String>()
    private var rejectedPermissions = ArrayList<String>()

    private lateinit var rv_locweatherhistory: RecyclerView
    private var rv_locweatherhistory_adapter: MyRecyclerViewAdapter? = null

    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        (application as WeatherLocApp).getWeatherLocAppComponent().inject(this)

        rv_locweatherhistory = find(R.id.rv_locweatherhistory)
        rv_locweatherhistory.layoutManager = LinearLayoutManager(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Location Service", Snackbar.LENGTH_LONG)
                .setAction("Start Location Updates") {
                    if (startLocationUpdates != null)
                        startLocationUpdates!!.dispose()
                    startLocationUpdates = StartLocationUpdates(locationUpdateProvider)
                    startLocationUpdates!!.execute(
                        MyLocationEventObserver(this@MainActivity),
                        null
                    )
                }
                .show()
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            ALL_PERMISSIONS_RESULT -> {
                for (permission in missingPermissions) {
                    if (!Permissions.hasPermission(permission, this))
                        rejectedPermissions.add(permission)
                }

                if (rejectedPermissions.size > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(rejectedPermissions[0])) {
                            Alerts.showMessageOKCancel(
                                "These permissions are mandatory for the location updates. Please grant these permssions to the application.",
                                DialogInterface.OnClickListener { dialog, which ->
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                        requestPermissions(rejectedPermissions.toTypedArray(), ALL_PERMISSIONS_RESULT)
                                },
                                DialogInterface.OnClickListener {dialog, which ->},
                                this@MainActivity
                            )
                            return
                        }
                    }
                } else {
                    Log.d(TAG, "All missing permissions have been granted - proceed to start location updates")
                    if (startLocationUpdates != null)
                        startLocationUpdates!!.dispose()
                    startLocationUpdates = StartLocationUpdates(locationUpdateProvider)
                    startLocationUpdates!!.execute(
                        MyLocationEventObserver(this@MainActivity),
                        null
                    )
                }
            }
        }
    }


    private var startLocationUpdates: StartLocationUpdates? = null
    private class MyLocationEventObserver(private val activity: MainActivity): LocationEventObserver() {
        override fun onNext(t: LocationEvent) {
            super.onNext(t)
            when (t.event) {
                Event.SERVICE_STOPPED -> {
                    if (activity.startLocationUpdates != null)
                        activity.startLocationUpdates!!.dispose()
                }
                Event.SERVICE_STOPPED_UNEXPECTEDLY -> {
                    if (activity.startLocationUpdates != null)
                        activity.startLocationUpdates!!.dispose()
                }
                Event.SERVICE_STARTING -> {}
                Event.SERVICE_CONNECTED -> {}
                Event.SERVICE_RUNNING -> {}
                Event.SERVICE_STOPPING -> {}
                Event.SERVICE_DISCONNECTING -> {}
                Event.SERVICE_DISCONNECTED -> {}
                Event.NO_GPS -> {TODO("ask user to enable")}
                Event.NO_NETWORK -> {TODO("ask user to enable")}
                Event.NO_GPS_OR_NETWORK -> {TODO("ask user to enable")}
                Event.MISSING_PERRMISSIONS -> {
                    if (t.permissions!!.isNotEmpty()) {
                        Log.d(TAG, String.format("Requesting missing permissings: %s", t.permissions.toString()))
                        activity.requestPermissions(t.permissions!!.toTypedArray(), ALL_PERMISSIONS_RESULT)
                    }
                }
                Event.LOCATION_CHANGED -> {
                    activity.runOnUiThread {
                        activity.mMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    t.coord!!.lat!!,
                                    t.coord!!.lon!!
                                ),
                                10.0F
                            )
                        )
                    }
                    if (activity.getCurrentLocationWeather != null)
                        activity.getCurrentLocationWeather!!.dispose()
                    activity.getCurrentLocationWeather = GetCurrentLocationWeather(activity.locationWeatherProvider)
                    activity.getCurrentLocationWeather!!.execute(MyGetCurrentLocationWeatherObserver(activity), t.coord!!)
                }
                Event.LOCATION_PLATFORM_PROVIDER_STATUS_CHANGED -> {}
                Event.LOCATION_PLATFORM_PROVIDER_ENABLED -> {}
                Event.LOCATION_PLATFORM_PROVIDER_DISABLED -> {}
            }
        }
    }

    private var getCurrentLocationWeather: GetCurrentLocationWeather? = null
    private class MyGetCurrentLocationWeatherObserver(private val activity: MainActivity): GetCurrentLocationWeatherResultObserver() {
        override fun onNext(t: LocationWeather) {
            super.onNext(t)
            if (activity.addNewLocationWeatherHistory != null)
                activity.addNewLocationWeatherHistory!!.dispose()
            activity.addNewLocationWeatherHistory = AddNewLocationWeatherHistory(activity.locationWeatherHistoryRepository)
            activity.addNewLocationWeatherHistory!!.execute(MyAddNewLocationWeatherHistoryObserver(activity), t)
        }

        override fun onError(e: Throwable) {
            super.onError(e)
        }
    }

    private var addNewLocationWeatherHistory: AddNewLocationWeatherHistory? = null
    private class MyAddNewLocationWeatherHistoryObserver(private val activity: MainActivity): AddNewLocationWeatherHistoryResultObserver() {
        override fun onNext(t: AddNewResult) {
            super.onNext(t)
            if (activity.getLocationWeatherHistory != null)
                activity.getLocationWeatherHistory!!.dispose()
            activity.getLocationWeatherHistory = GetLocationWeatherHistory(activity.locationWeatherHistoryRepository)
            activity.getLocationWeatherHistory!!.execute(MyGetLocationWeatherHistoryResultObserver(activity), null)
        }

        override fun onError(e: Throwable) {
            super.onError(e)
        }
    }

    private var getLocationWeatherHistory: GetLocationWeatherHistory? = null
    private class MyGetLocationWeatherHistoryResultObserver(private val activity: MainActivity): GetLocationWeatherHistoryResultObserver() {
        override fun onNext(t: List<LocationWeather>) {
            super.onNext(t)
            for ((i, locationWeather) in t.withIndex()) {
                Log.d(TAG, String.format("MyGetLocationWeatherHistoryResultObserver::onNext: locationWeather[%d]: %s", i, locationWeather.toString()))
                activity.runOnUiThread {
                    activity.rv_locweatherhistory_adapter = MyRecyclerViewAdapter(activity, t)
                    activity.rv_locweatherhistory.adapter = activity.rv_locweatherhistory_adapter
                }
            }
        }

        override fun onError(e: Throwable) {
            super.onError(e)
        }
    }
}

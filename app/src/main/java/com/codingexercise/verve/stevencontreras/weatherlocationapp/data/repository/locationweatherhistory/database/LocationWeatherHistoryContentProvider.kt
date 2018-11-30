package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.di.DaggerLocationWeatherDatabaseClientComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.di.LocationWeatherDatabaseClientComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.di.LocationWeatherDatabaseClientModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.history.GetLocationWeatherHistory
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.interactor.usecase.location.weather.history.GetLocationWeatherHistoryResultObserver
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.weather.LocationWeather
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherHistoryRepository
import java.lang.IllegalArgumentException
import java.lang.reflect.Field
import java.util.*
import javax.inject.Inject

class LocationWeatherHistoryContentProvider : ContentProvider() {

    companion object {
        val TAG = LocationWeatherHistoryContentProvider::class.java.simpleName

        val AUTHORITY = "com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.LocationWeatherHistoryContentProvider"
        private val LWD_TABLE = "locationweatherdetails"
        val CONTENT_URI : Uri = Uri.parse("content://$AUTHORITY/$LWD_TABLE")
    }

    private enum class URIType {
        ALL_LOCATION_DETAILS
        , SPECIFIC_LOCATION_DETAILS
        ;

        companion object {
            fun fromInt(i: Int): URIType? {
                when (i) {
                    0 -> {return ALL_LOCATION_DETAILS}
                    1 -> {return SPECIFIC_LOCATION_DETAILS}
                    else -> return null
                }
            }
        }
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    private lateinit var locationWeatherDatabaseClientComponent: LocationWeatherDatabaseClientComponent

    @Inject
    protected lateinit var locationWeatherHistoryRepository: LocationWeatherHistoryRepository

    init {
        uriMatcher.addURI(AUTHORITY, LWD_TABLE, URIType.ALL_LOCATION_DETAILS.ordinal)
        uriMatcher.addURI(AUTHORITY, LWD_TABLE + "/#", URIType.SPECIFIC_LOCATION_DETAILS.ordinal)
    }


    override fun onCreate(): Boolean {
        locationWeatherDatabaseClientComponent = DaggerLocationWeatherDatabaseClientComponent.builder()
            .locationWeatherDatabaseClientModule(LocationWeatherDatabaseClientModule(context))
            .build()
        locationWeatherDatabaseClientComponent.inject(this)
        return true
    }

    private class LWCPGetLocationWeatherHistoryResultObserver: GetLocationWeatherHistoryResultObserver() {
        override fun onNext(t: List<LocationWeather>) {
            super.onNext(t)
            for ((i, locationWeather) in t.withIndex()) {
                Log.d(TAG, String.format("LWCPGetLocationWeatherHistoryResultObserver::onNext: locationWeather[%d]: %s", i, locationWeather.toString()))
            }
        }

        override fun onError(e: Throwable) {
            super.onError(e)
        }
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val uriType = URIType.fromInt(uriMatcher.match(uri)) ?: throw IllegalArgumentException("Unknown URI")

        val getLocationWeatherHistory = GetLocationWeatherHistory(locationWeatherHistoryRepository)
        val getLocationWeatherWeatherHistoryResultObserver = LWCPGetLocationWeatherHistoryResultObserver()

        val flds = LocationWeather::class.java.declaredFields
        val cols = Array<String>(flds.size){""}
        for (i in 0 until flds.size)
            cols[i] = flds[i].name
        val cursor = MatrixCursor(cols)

        try {
            when (uriType) {
                URIType.ALL_LOCATION_DETAILS -> {
                    getLocationWeatherHistory.execute(getLocationWeatherWeatherHistoryResultObserver, null)
                    getLocationWeatherWeatherHistoryResultObserver.awaitCompletion()
                    for ((m, locationWeather) in getLocationWeatherWeatherHistoryResultObserver.resultList.withIndex()) {
                        val vals = Array<Any?>(flds.size){null}
                        for ((n, fld) in locationWeather::class.java.declaredFields.withIndex()) {
                            fld.isAccessible = true
                            val targetType = fld.type
                            //val objectValue = targetType.newInstance()
                            val value = fld.get(locationWeather)
                            Log.d(TAG, String.format("query/ALL_LOCATION_DETAILS: extracted field[%d] \"%s\" (type %s, val ->%s<-) from locationWeather[%d]", n, fld.name, targetType, value, m))
                            vals[n] = value
                        }
                        cursor.addRow(vals)
                    }
                }
                URIType.SPECIFIC_LOCATION_DETAILS -> {

                }
            }
        } finally {
            getLocationWeatherHistory.dispose()
            getLocationWeatherWeatherHistoryResultObserver.dispose()
        }

        return cursor
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO("Implement this to handle requests for the MIME type of the data" +
                "at the given URI")
    }

    override fun insert(uri: Uri, values: ContentValues): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}

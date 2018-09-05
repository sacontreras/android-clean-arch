package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database

import android.content.Context
import android.database.sqlite.SQLiteAbortException
import android.util.Log
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.dao.LocationWeatherDb
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.model.LocationWeatherDetailsEntity
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.model.WeatherEntity
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.weather.LocationWeather
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.weather.Weather
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.AddNewException
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.AddNewResult
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherHistoryRepository
import io.reactivex.Observable
import org.modelmapper.ModelMapper

class LocationWeatherDatabaseClient(private val context: Context): LocationWeatherHistoryRepository {
    companion object {
        private val TAG = LocationWeatherDatabaseClient::class.java.simpleName
    }
    private val locationWeatherDb: LocationWeatherDb = LocationWeatherDb.getInstance(context)

    //O(n*m)!! YUCK!
    override fun get(lwdid: Long?): Observable<List<LocationWeather>> {
        return Observable.create<List<LocationWeather>> { emitter ->
            val locationWeatherDetailsEntities: List<LocationWeatherDetailsEntity> = if (lwdid != null) locationWeatherDb.getLocationWeatherDao().get(lwdid) else locationWeatherDb.getLocationWeatherDao().getAll()
            if (locationWeatherDetailsEntities.isNotEmpty()) {
                val list_locationWeather = ArrayList<LocationWeather>()
                val modelMapper = ModelMapper()
                for (locationWeatherDetailsEntity in locationWeatherDetailsEntities) {  //n
                    val locationWeather = modelMapper.map(locationWeatherDetailsEntity, LocationWeather::class.java)
                    locationWeather.weather = ArrayList<Weather>()
                    val weatherEntities = locationWeatherDb.getLocationWeatherDao().getWeather(locationWeatherDetailsEntity.lwdId!!)
                    for ((i, weatherEntity) in weatherEntities.withIndex()) {    //m
                        Log.d(TAG, String.format("mapping from weatherEntity[%d].weather: %s", i, weatherEntity.weather.toString()))
                        val weather = modelMapper.map(weatherEntity.weather, Weather::class.java)
                        Log.d(TAG, String.format("... to weatherDTO[%d]: %s", i, weather.toString()))
                        (locationWeather.weather as ArrayList<Weather>).add(weather)
                    }
                    list_locationWeather.add(locationWeather)
                }
                emitter.onNext(list_locationWeather)
            }
            emitter.onComplete()
        }
    }

    override fun addNew(locationWeather: LocationWeather): Observable<AddNewResult> {
        return Observable.create<AddNewResult> { emitter ->
            val modelMapper = ModelMapper()
            val locationWeatherDetailsEntity = modelMapper.map(locationWeather, LocationWeatherDetailsEntity::class.java)
            try {
                val lwdid = locationWeatherDb.getLocationWeatherDao().insert(locationWeatherDetailsEntity)
                Log.d(TAG, String.format("addNew: result lwdid: %d", lwdid))
                val wids = ArrayList<Long>()
                if (locationWeather.weather != null && locationWeather.weather!!.isNotEmpty()) {
                    var i = 0
                    for (weather in locationWeather.weather!!) {
                        val weatherEntity = modelMapper.map(weather, WeatherEntity::class.java)
                        weatherEntity.fk_lwdId = lwdid
                        val wid = locationWeatherDb.getLocationWeatherDao().insert(weatherEntity)
                        Log.d(TAG, String.format("addNew:\tresult wid[%d]: %d", i++, wid))
                        wids.add(wid)
                    }
                } else {
                    throw SQLiteAbortException()
                }
                emitter.onNext(AddNewResult(lwdid, wids))
                emitter.onComplete()
            } catch (e: Exception) {
                val addNewException = AddNewException(e.message ?: "", e.cause ?: e)
                emitter.onError(addNewException)
            }
        }
    }

    override fun delete(lwdid: Long): Observable<Boolean> {
        return Observable.create<Boolean> { emitter ->
            locationWeatherDb.getLocationWeatherDao().deleteWeather(lwdid)
            locationWeatherDb.getLocationWeatherDao().deleteLocationWeatherDetails(lwdid)
            emitter.onNext(true)
            emitter.onComplete()
        }
    }
}
package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.dao

import android.arch.persistence.room.*
import android.content.Context
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.model.LocationWeatherDetailsEntity
import com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.model.WeatherEntity

@Dao
interface LocationWeatherDao {
    /****************** Create: begin ******************/
    @Insert //(onConflict = ABORT)    //default is OnConflictStrategy.ABORT
    fun insert(locationWeatherDetailsEntity: LocationWeatherDetailsEntity): Long

    @Insert //(onConflict = ABORT)    //default is OnConflictStrategy.ABORT
    fun insert(weatherEntity: WeatherEntity): Long
    /****************** Create: end ******************/


    /****************** Read: begin ******************/
    @Query("SELECT * FROM locationweatherdetails WHERE lwdId=:lwdid")
    fun get(lwdid: Long): List<LocationWeatherDetailsEntity>

    @Query("SELECT * FROM locationweatherdetails")
    fun getAll(): List<LocationWeatherDetailsEntity>

    @Query("SELECT * FROM weather WHERE fk_lwdId=:lwdId")
    fun getWeather(lwdId: Long): List<WeatherEntity>
    /****************** Read: end ******************/


    /****************** Update: begin ******************/
    //      no need for updates yet
    /****************** Update: end ******************/


    /****************** Delete: begin ******************/
    @Query("DELETE FROM locationweatherdetails WHERE lwdId=:lwdid")
    fun deleteLocationWeatherDetails(lwdid: Long)

    @Query("DELETE FROM weather WHERE wid=:wid")
    fun deleteSingleWeather(wid: Long)

    @Query("DELETE FROM weather WHERE fk_lwdId=:lwdid")
    fun deleteWeather(lwdid: Long)
    /****************** Delete: end ******************/
}


@Database(
    entities = arrayOf(LocationWeatherDetailsEntity::class, WeatherEntity::class),
    version = 1
)
abstract class LocationWeatherDb: RoomDatabase() {
    companion object {
        private val DB_NAME = "locationweather.db"

        @Volatile
        private var instance: LocationWeatherDb? = null

        private fun create(context: Context): LocationWeatherDb {
            return Room.databaseBuilder(context, LocationWeatherDb::class.java, DB_NAME)
                .build()
        }

        @Synchronized
        fun getInstance(context: Context): LocationWeatherDb {
            val temp: LocationWeatherDb = instance ?: create(context)
            if (instance == null)
                instance = temp
            return temp
        }
    }

    abstract fun getLocationWeatherDao(): LocationWeatherDao
}
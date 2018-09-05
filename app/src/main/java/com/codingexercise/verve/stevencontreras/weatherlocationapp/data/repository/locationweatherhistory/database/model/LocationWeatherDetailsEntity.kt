package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.model

import android.arch.persistence.room.*
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.Coord
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.weather.*

@Entity(tableName = "locationweatherdetails")
data class LocationWeatherDetailsEntity @JvmOverloads constructor(
        @PrimaryKey(autoGenerate = true)
    var lwdId: Long? = null,

    //    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    //    @TypeConverters(DateConverter::class)
    //    var timestamp: Date = Date(System.currentTimeMillis()),
        var timestamp: Long = System.currentTimeMillis(),

        @Embedded(prefix = "coord_")
    var coord: Coord? = null,

    //one-to-many relationship from this to WeatherEntity - but not embedded! we need to compose

        var base: String? = null,

        @Embedded(prefix = "main_")
    var main: Main? = null,

        var visibility: Double? = null,

        @Embedded(prefix = "wind_")
    var wind: Wind? = null,

        @Embedded(prefix = "clouds_")
    var clouds: Clouds? = null,

        var dt: Int? = null,

        @Embedded(prefix = "sys_")
    var sys: Sys? = null,

        var id: Int? = null,

        var name: String? = null,

        var cod: Int? = null
)
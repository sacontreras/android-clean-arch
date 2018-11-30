package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.weather.Weather

@Entity(
    tableName = "weather",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = LocationWeatherDetailsEntity::class,
            parentColumns = arrayOf("lwdId"),
            childColumns = arrayOf("fk_lwdId"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class WeatherEntity @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    var wId: Long? = null,

    @Embedded
    var weather: Weather? = null,

    var fk_lwdId: Long? = null
)
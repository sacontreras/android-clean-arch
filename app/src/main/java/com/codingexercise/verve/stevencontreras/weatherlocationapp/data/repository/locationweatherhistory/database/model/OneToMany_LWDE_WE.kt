package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherhistory.database.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

data class OneToMany_LWDE_WE @JvmOverloads constructor(
    @Embedded
    var locationWeatherDetailsEntity: LocationWeatherDetailsEntity? = null,

    @Relation(
        parentColumn = "_id",
        entityColumn = "fk_lwdetails_id"
    )
    var weather: List<WeatherEntity>? = ArrayList()
)
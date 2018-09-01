package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.local.database.model

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id
import org.greenrobot.greendao.annotation.NotNull
import org.greenrobot.greendao.annotation.Property


@Entity(nameInDb = "locationweather")
data class LocationWeatherEntity @JvmOverloads constructor(
    @Id(autoincrement = true)
    var id: Long = -1,

    @Property(nameInDb = "dt")
    @NotNull
    var dt: Long = -1,

    @Property(nameInDb = "lw_id")
    @NotNull
    var lw_id: Long = -1,

    @Property(nameInDb = "name")
    @NotNull
    var name: String = "",       //note that this corresponds to the city name

    @Property(nameInDb = "cod")
    @NotNull
    var cod: Int = 0
)
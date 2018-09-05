package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.weather

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.Coord
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationWeather @JvmOverloads constructor(
    @SerializedName("coord")
    @Expose
    var coord: Coord? = null,

    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null,

    @SerializedName("base")
    @Expose
    var base: String? = null,

    @SerializedName("main")
    @Expose
    var main: Main? = null,

    @SerializedName("visibility")
    @Expose
    var visibility: Double? = null,

    @SerializedName("wind")
    @Expose
    var wind: Wind? = null,

    @SerializedName("clouds")
    @Expose
    var clouds: Clouds? = null,

    @SerializedName("dt")
    @Expose
    var dt: Int? = null,

    @SerializedName("sys")
    @Expose
    var sys: Sys? = null,

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("cod")
    @Expose
    var cod: Int? = null
)
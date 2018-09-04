package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coord @JvmOverloads constructor(
    @SerializedName("lon")
    @Expose
    var lon: Double? = null,

    @SerializedName("lat")
    @Expose
    var lat: Double? = null
)
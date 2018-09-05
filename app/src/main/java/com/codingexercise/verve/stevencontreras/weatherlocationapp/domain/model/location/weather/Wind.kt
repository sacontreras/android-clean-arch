package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Wind @JvmOverloads constructor(
    @SerializedName("speed")
    @Expose
    var speed: Double? = null,

    @SerializedName("deg")
    @Expose
    var deg: Double? = null
)
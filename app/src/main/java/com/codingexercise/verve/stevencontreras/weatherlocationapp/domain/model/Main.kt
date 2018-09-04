package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Main @JvmOverloads constructor(
    @SerializedName("temp")
    @Expose
    var temp: Double? = null,

    @SerializedName("pressure")
    @Expose
    var pressure: Double? = null,

    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null,

    @SerializedName("temp_min")
    @Expose
    var temp_min: Double? = null,

    @SerializedName("temp_max")
    @Expose
    var temp_max: Double? = null
)
package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Sys @JvmOverloads constructor(
    @SerializedName("type")
    @Expose
    var type: Double? = null,

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("message")
    @Expose
    var message: Double? = null,

    @SerializedName("country")
    @Expose
    var country: String? = null,

    @SerializedName("sunrise")
    @Expose
    var sunrise: Int? = null,

    @SerializedName("sunset")
    @Expose
    var sunset: Int? = null
)
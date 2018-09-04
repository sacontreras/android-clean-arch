package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Clouds @JvmOverloads constructor(
    @SerializedName("all")
    @Expose
    var all: Int? = null
)
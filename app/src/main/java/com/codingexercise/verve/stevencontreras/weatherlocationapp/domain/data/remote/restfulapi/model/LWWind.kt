package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LWWind {

    @SerializedName("speed")
    @Expose
    var speed: Double? = null
    @SerializedName("deg")
    @Expose
    var deg: Int? = null

}

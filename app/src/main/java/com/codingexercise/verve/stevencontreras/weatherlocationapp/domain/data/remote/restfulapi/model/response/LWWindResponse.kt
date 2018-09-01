package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LWWindResponse {

    @SerializedName("speed")
    @Expose
    var speed: Double? = null
    @SerializedName("deg")
    @Expose
    var deg: Double? = null

}

package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LWCoord {

    @SerializedName("lon")
    @Expose
    var lon: Double? = null
    @SerializedName("lat")
    @Expose
    var lat: Double? = null

}

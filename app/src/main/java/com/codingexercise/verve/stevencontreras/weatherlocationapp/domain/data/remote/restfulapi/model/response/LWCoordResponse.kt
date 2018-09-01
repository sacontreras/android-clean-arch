package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LWCoordResponse {

    @SerializedName("lon")
    @Expose
    var lon: Double? = null
    @SerializedName("lat")
    @Expose
    var lat: Double? = null

}

package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LWMainResponse {

    @SerializedName("temp")
    @Expose
    var temp: Double? = null
    @SerializedName("pressure")
    @Expose
    var pressure: Double? = null
    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null
    @SerializedName("temp_min")
    @Expose
    var tempMin: Double? = null
    @SerializedName("temp_max")
    @Expose
    var tempMax: Double? = null

}

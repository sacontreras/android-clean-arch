package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LocationWeatherResponse {

    @SerializedName("coord")
    @Expose
    var coord: LWCoordResponse? = null
    @SerializedName("weather")
    @Expose
    var weather: List<LWWeatherResponse>? = null
    @SerializedName("base")
    @Expose
    var base: String? = null
    @SerializedName("main")
    @Expose
    var main: LWMainResponse? = null
    @SerializedName("visibility")
    @Expose
    var visibility: Double? = null
    @SerializedName("wind")
    @Expose
    var wind: LWWindResponse? = null
    @SerializedName("clouds")
    @Expose
    var clouds: LWCloudsResponse? = null
    @SerializedName("dt")
    @Expose
    var dt: Int? = null
    @SerializedName("sys")
    @Expose
    var sys: LWSysResponse? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("cod")
    @Expose
    var cod: Int? = null

}

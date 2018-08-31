package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LocationWeather {

    @SerializedName("coord")
    @Expose
    var coord: LWCoord? = null
    @SerializedName("weather")
    @Expose
    var weather: List<LWWeather>? = null
    @SerializedName("base")
    @Expose
    var base: String? = null
    @SerializedName("main")
    @Expose
    var main: LWMain? = null
    @SerializedName("visibility")
    @Expose
    var visibility: Int? = null
    @SerializedName("wind")
    @Expose
    var wind: LWWind? = null
    @SerializedName("clouds")
    @Expose
    var clouds: LWClouds? = null
    @SerializedName("dt")
    @Expose
    var dt: Int? = null
    @SerializedName("sys")
    @Expose
    var sys: LWSys? = null
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

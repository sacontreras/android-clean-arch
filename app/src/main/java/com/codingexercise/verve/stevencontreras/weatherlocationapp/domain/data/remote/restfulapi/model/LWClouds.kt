package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LWClouds {

    @SerializedName("all")
    @Expose
    var all: Int? = null

}

package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.data.remote.restfulapi.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LWCloudsResponse {

    @SerializedName("all")
    @Expose
    var all: Int? = null

}

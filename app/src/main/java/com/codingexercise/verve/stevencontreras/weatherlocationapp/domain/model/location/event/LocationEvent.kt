package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.Coord
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationEvent @JvmOverloads constructor(
    @SerializedName("event")
    @Expose
    var event: Event,

    @SerializedName("coord")
    @Expose
    var coord: Coord? = null,

    @SerializedName("platformproviderstatus")
    @Expose
    var platformproviderstatus: PlatformProviderStatus? = null,

    @SerializedName("permissions")
    @Expose
    var permissions: List<String>? = null,

    @SerializedName("exception")
    @Expose
    var exception: Exception? = null
)
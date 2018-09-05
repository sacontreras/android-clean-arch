package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlatformProviderStatus(
    @SerializedName("code")
    @Expose
    var platformprovider: String? = null,

    @SerializedName("code")
    @Expose
    var code: Int? = null,

    @SerializedName("description")
    @Expose
    var description: String? = null
)
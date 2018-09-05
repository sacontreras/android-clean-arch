package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationprovider.android

import android.os.Binder

class FGSBinder(private val instance: FGS): Binder() {
    fun getService(): FGS {
        return instance
    }
}
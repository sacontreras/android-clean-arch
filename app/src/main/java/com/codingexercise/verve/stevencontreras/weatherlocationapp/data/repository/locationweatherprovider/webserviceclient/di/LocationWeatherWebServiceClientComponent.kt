package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherprovider.webserviceclient.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(LocationWeatherWebServiceClientModule::class))
interface LocationWeatherWebServiceClientComponent
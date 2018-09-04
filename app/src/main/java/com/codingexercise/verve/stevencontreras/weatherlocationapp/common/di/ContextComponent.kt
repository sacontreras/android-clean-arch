package com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ContextModule::class))
interface ContextComponent
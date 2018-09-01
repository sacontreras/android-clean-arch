package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.component

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.module.ContextModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ContextModule::class))
interface ContextComponent
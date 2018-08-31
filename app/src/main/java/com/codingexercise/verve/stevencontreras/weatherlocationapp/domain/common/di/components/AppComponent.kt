package com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.components

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.common.di.modules.AppModule
import com.codingexercise.verve.stevencontreras.weatherlocationapp.presentation.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(target: MainActivity)
}
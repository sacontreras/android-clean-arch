package com.codingexercise.verve.stevencontreras.weatherlocationapp.common.di

import com.codingexercise.verve.stevencontreras.weatherlocationapp.presentation.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(target: MainActivity)
}
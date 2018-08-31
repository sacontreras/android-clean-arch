package com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }
}
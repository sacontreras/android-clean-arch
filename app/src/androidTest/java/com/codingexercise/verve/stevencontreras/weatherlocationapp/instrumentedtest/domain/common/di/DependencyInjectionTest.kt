package com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.components.ContextComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.components.DaggerContextComponent
import com.codingexercise.verve.stevencontreras.weatherlocationapp.instrumentedtest.domain.common.di.modules.ContextModule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DependencyInjectionTest {
    private val testContextComponent: ContextComponent = DaggerContextComponent.builder()
        .contextModule(ContextModule(InstrumentationRegistry.getTargetContext()))
        .build()

    //private val testLocationWeatherServiceComponent: TestLocationWeatherServiceComponent

    @Test
    fun testContextModuleProvided() {
        assert(testContextComponent != null)
    }

    @Test
    fun testContextInjected() {
        val testDependencyInjectionTarget = DependencyInjectionTarget()
        testContextComponent.inject(testDependencyInjectionTarget)
        assert(testDependencyInjectionTarget.testContextComponent != null)
    }
}
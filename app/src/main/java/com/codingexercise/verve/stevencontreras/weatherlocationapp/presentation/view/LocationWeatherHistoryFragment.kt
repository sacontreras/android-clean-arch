package com.codingexercise.verve.stevencontreras.weatherlocationapp.presentation.view

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingexercise.verve.stevencontreras.weatherlocationapp.R

/**
 * A placeholder fragment containing a simple view.
 */
class LocationWeatherHistoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_locationweatherhistory, container, false)
    }
}

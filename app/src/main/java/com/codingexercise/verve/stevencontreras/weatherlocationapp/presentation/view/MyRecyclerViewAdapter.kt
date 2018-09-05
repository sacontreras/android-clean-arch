package com.codingexercise.verve.stevencontreras.weatherlocationapp.presentation.view

import android.content.Context
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.codingexercise.verve.stevencontreras.weatherlocationapp.R
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.weather.LocationWeather
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import java.util.*


class MyRecyclerViewAdapter(private val mContext: Context, private val listLocationWeather: List<LocationWeather>?): RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder>() {
    companion object {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CustomViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_row, null)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(customViewHolder: CustomViewHolder, i: Int) {
        val locationWeather = listLocationWeather!![i]

        //Render image using Picasso library
        if (!TextUtils.isEmpty(locationWeather.weather!![0].icon)) {
            Picasso.get()
                .load(String.format("http://openweathermap.org/img/w/%s.png", locationWeather.weather!![0].icon))
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(customViewHolder.weathericon)
        }

        customViewHolder.city.text = Html.fromHtml(locationWeather.name)
        customViewHolder.location.text = Html.fromHtml(String.format("LAT: %f, LON: %f", locationWeather.coord!!.lat, locationWeather.coord!!.lon))
        customViewHolder.timestamp.text = Html.fromHtml(sdf.format(java.util.Date(locationWeather.timestamp!!)))
        customViewHolder.description.text = Html.fromHtml(String.format("%fF, %s", locationWeather.main!!.temp, locationWeather.weather!![0].description))
    }

    override fun getItemCount(): Int {
        return listLocationWeather?.size ?: 0
    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val weathericon = view.find<ImageView>(R.id.thumbnail)
        val city = view.find<TextView>(R.id.city)
        val location = view.find<TextView>(R.id.location)
        val timestamp = view.find<TextView>(R.id.timestamp)
        val description = view.find<TextView>(R.id.description)
    }
}
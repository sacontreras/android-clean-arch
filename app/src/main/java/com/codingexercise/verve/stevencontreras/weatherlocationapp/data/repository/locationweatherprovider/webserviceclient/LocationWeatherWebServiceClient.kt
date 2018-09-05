package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationweatherprovider.webserviceclient

import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.weather.LocationWeather
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.repository.LocationWeatherProvider
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class LocationWeatherWebServiceClient(private val key: String): LocationWeatherProvider {
    companion object {
        const val BASE_URL: String = "https://api.openweathermap.org"
    }

    private interface LocationWeatherWebService {
        @GET("/data/2.5/weather")
        fun getCurrentLocationWeather(
            @Query("lon") lon: Double,
            @Query("lat") lat: Double,
            @Query("APPID") appId: String
        ): Call<LocationWeather>
    }

    private val locationWeatherWebServiceClient: LocationWeatherWebService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create()) //maps body to json representation of LocationWeather model
        .build()
        .create(LocationWeatherWebService::class.java)

    override fun get(lon: Double, lat: Double): Observable<LocationWeather> {
        return Observable.create<LocationWeather> { emitter ->
            try {
                val call_getCurrentLocationWeather = locationWeatherWebServiceClient.getCurrentLocationWeather(lon, lat, key)
                //val response = call_getCurrentLocationWeather.execute()
                call_getCurrentLocationWeather.enqueue(
                    object: Callback<LocationWeather> {
                        override fun onResponse(call: Call<LocationWeather>, response: Response<LocationWeather>) {
                            emitter.onNext(response.body()!!)
                            emitter.onComplete()
                        }

                        override fun onFailure(call: Call<LocationWeather>, t: Throwable) {
                            emitter.onError(t)
                        }
                    }
                )
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }
}
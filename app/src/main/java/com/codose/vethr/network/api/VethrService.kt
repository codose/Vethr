package com.codose.vethr.network.api

import com.codose.vethr.network.response.weatherResponse.WeatherResponse
import com.codose.vethr.utils.Constants.API_KEY
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface VethrService {
    @GET("/weather")
    fun getWeatherData(@Query("lat") lat : Double,
                       @Query("lon") lon : Double,
                       @Query("exclude") exclude : List<String> = listOf("hourly","minutely"),
                       @Query("appId") appId : String = API_KEY, @Query("units") units : String = "metric") : Deferred<WeatherResponse>
}
package com.codose.vethr.network.api

import com.codose.vethr.network.response.searchResponse.PlaceResponse
import com.codose.vethr.network.response.weatherResponse.WeatherResponse
import com.codose.vethr.utils.Constants.API_KEY
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface VethrService {
    @GET("data/2.5/onecall")
    fun getWeatherData(@Query("lat") lat : Double,
                       @Query("lon") lon : Double,
                       @Query("exclude") exclude : List<String> = listOf("hourly","minutely"),
                       @Query("appId") appId : String = API_KEY, @Query("units") units : String = "metric") : Deferred<WeatherResponse>

    @GET("v1/discover")
    fun getPlaces(@Query("q") query : String,
                  @Query("limit") limit : String = "20",
                  @Query("apiKey") key : String = "TaeGeDn_vUCNV_4FSBV-iz5E8mCtumY_cZ2vR5b3okQ",
                  @Query("at") log_lat : String = "6.465422,3.406448") : Deferred<PlaceResponse>
}
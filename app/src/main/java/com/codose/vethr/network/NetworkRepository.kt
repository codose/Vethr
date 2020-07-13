package com.codose.vethr.network

import android.app.DownloadManager
import com.codose.vethr.network.api.RetrofitClient
import com.codose.vethr.network.response.searchResponse.PlaceResponse
import com.codose.vethr.network.response.weatherResponse.WeatherResponse
import com.codose.vethr.utils.Resource

class NetworkRepository {
    private val weatherService = RetrofitClient.vethrService()
    private val placesService = RetrofitClient.placeSearch()


    suspend fun getWeatherData(lat:Double,long: Double) : Resource<WeatherResponse>{
        return try {
            val data = weatherService.getWeatherData(lat,long).await()
            Resource.Success(data)
        }catch (e:Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun searchPlaces(query: String) : Resource<PlaceResponse>{
        return try {
            val data = placesService.getPlaces(query).await()
            Resource.Success(data)
        }catch (e:Exception){
            Resource.Failure(e.message!!)
        }
    }
}
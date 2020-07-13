package com.codose.vethr.network

import com.codose.vethr.network.api.RetrofitClient
import com.codose.vethr.network.response.weatherResponse.WeatherResponse
import com.codose.vethr.utils.Resource

class NetworkRepository {
    val weatherService = RetrofitClient.vethrService()

    suspend fun getWeatherData(lat:Double,long: Double) : Resource<WeatherResponse>{
        return try {
            val data = weatherService.getWeatherData(lat,long).await()
            Resource.Success(data)
        }catch (e:Exception){
            Resource.Failure(e.message!!)
        }
    }
}
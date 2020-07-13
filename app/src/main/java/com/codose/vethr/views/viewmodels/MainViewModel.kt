package com.codose.vethr.views.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codose.vethr.network.NetworkRepository
import com.codose.vethr.network.response.weatherResponse.WeatherResponse
import com.codose.vethr.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    val repository = NetworkRepository()
    val weatherData = MutableLiveData<Resource<WeatherResponse>>()

    fun getWeatherData(lat:Double,long: Double){
        weatherData.value = Resource.Loading()
        viewModelScope.launch {
            weatherData.value = withContext(Dispatchers.IO){
                repository.getWeatherData(lat, long)
            }
        }
    }
}
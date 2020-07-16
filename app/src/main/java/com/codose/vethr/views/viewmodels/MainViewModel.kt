package com.codose.vethr.views.viewmodels

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.location.Location
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codose.vethr.models.Favourite
import com.codose.vethr.network.NetworkRepository
import com.codose.vethr.network.response.searchResponse.PlaceResponse
import com.codose.vethr.network.response.weatherResponse.WeatherResponse
import com.codose.vethr.utils.Resource
import io.nlopez.smartlocation.OnLocationUpdatedListener
import io.nlopez.smartlocation.SmartLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    val repository = NetworkRepository(context)
    val weatherData = MutableLiveData<Resource<WeatherResponse>>()
    val placeData = MutableLiveData<Resource<PlaceResponse>>()
    val location = MutableLiveData<Resource<Location>>()
    val allFavs = repository.getAllFav()
    val locationString = MutableLiveData<Resource<String>>()


    init {
        getLocation()

    }


    fun getWeatherData(lat:Double,long: Double){
        weatherData.value = Resource.Loading()
        viewModelScope.launch {
            weatherData.value = withContext(Dispatchers.IO){
                repository.getWeatherData(lat, long)
            }
        }
    }

    fun getLocation(){
        location.value = Resource.Loading()
        SmartLocation.with(context).location()
            .oneFix()
            .start {
                if(it!=null){
                    getUserLocation("${it.latitude},${it.longitude}")
                    getWeatherData(it.latitude, it.longitude)
                    location.value = Resource.Success(it)
                }else{
                    location.value = Resource.Failure("Unable to get Location")
                }
            }
    }

    fun searchPlace(query: String){
        placeData.value = Resource.Loading()
        viewModelScope.launch {
            placeData.value = withContext(Dispatchers.IO){
                repository.searchPlaces(query)
            }
        }
    }

    fun getUserLocation(query: String){
        locationString.value = Resource.Loading()
        viewModelScope.launch {
            locationString.value = withContext(Dispatchers.IO){
                repository.getLocation(query)
            }
        }
    }

    fun insertFav(favourite: Favourite) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.insertFav(favourite)
            }
        }
    }

    fun deleteFav(favourite: Favourite) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.deleteFav(favourite)
            }
        }
    }
}
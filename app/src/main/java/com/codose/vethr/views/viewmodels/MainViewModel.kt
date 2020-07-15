package com.codose.vethr.views.viewmodels

import android.app.DownloadManager
import android.content.Context
import android.location.Location
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codose.vethr.network.NetworkRepository
import com.codose.vethr.network.response.searchResponse.PlaceResponse
import com.codose.vethr.network.response.weatherResponse.WeatherResponse
import com.codose.vethr.utils.Resource
import io.nlopez.smartlocation.OnLocationUpdatedListener
import io.nlopez.smartlocation.SmartLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(context: Context) : ViewModel() {
    val repository = NetworkRepository()


    private val _weatherData = MutableLiveData<Resource<WeatherResponse>>()
    val weatherData: LiveData<Resource<WeatherResponse>>
    get() = _weatherData

    // The internal MutableLiveData String that stores the most recent response
    private val _placeData = MutableLiveData<Resource<PlaceResponse>>()

    // The external immutable LiveData for the response String
    val placeData: LiveData<Resource<PlaceResponse>>
    get() = _placeData


    private val _location = MutableLiveData<Resource<Location>>()
    val location: LiveData<Resource<Location>>
    get() = _location

    init {
        getLocation(context)
    }


    fun getWeatherData(lat:Double,long: Double){
        _weatherData.value = Resource.Loading()
        viewModelScope.launch {
            _weatherData.value = withContext(Dispatchers.IO){
                repository.getWeatherData(lat, long)
            }
        }
    }

    fun getLocation(context:Context){
        _location.value = Resource.Loading()
        SmartLocation.with(context).location()
            .oneFix()
            .start {
                if(it!=null){
                    _location.value = Resource.Success(it)
                }else{
                    _location.value = Resource.Failure("Unable to get Location")
                }
            }
    }

    fun searchPlace(query: String){
        _placeData.value = Resource.Loading()
        viewModelScope.launch {
            _placeData.value = withContext(Dispatchers.IO){
                repository.searchPlaces(query)
            }
        }
    }
}
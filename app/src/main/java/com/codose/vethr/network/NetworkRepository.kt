package com.codose.vethr.network

import android.app.DownloadManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.codose.vethr.models.Favourite
import com.codose.vethr.models.room.FavoriteDatabase
import com.codose.vethr.network.api.RetrofitClient
import com.codose.vethr.network.response.location.LocationResponse
import com.codose.vethr.network.response.searchResponse.PlaceResponse
import com.codose.vethr.network.response.weatherResponse.WeatherResponse
import com.codose.vethr.utils.Resource

class NetworkRepository(val context: Context) {
    private val weatherService = RetrofitClient.vethrService()
    private val placesService = RetrofitClient.placeSearch()
    private val locationService = RetrofitClient.locationService()

    private val database : FavoriteDatabase = FavoriteDatabase.getInstance(context)

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

    suspend fun getLocation(query: String) : Resource<String>{
        return try {
            val data = locationService.getLocation(query).await()
            val location = "${data.items[0].address.state},${data.items[0].address.countryName}"
            Resource.Success(location)
        }catch (e:Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun insertFav(favourite: Favourite): String {
        return try {
            database.favoriteDao.insert(favourite)
            Log.e("FavoriteDatabase","Insert Success")
            "Successful"
        }catch (e: Exception){
            Log.e("FavoriteDatabase",e.message!!)
            e.message!!
        }
    }

    suspend fun deleteFav(favourite: Favourite): String {
        return try {
            database.favoriteDao.delete(favourite)
            Log.e("FavoriteDatabase","Delete Success")
            "Delete Successful"
        }catch (e: Exception){
            Log.e("FavoriteDatabase",e.message!!)
            e.message!!
        }
    }

    fun getAllFav(): LiveData<List<Favourite>>? {
        return try {
            database.favoriteDao.getAllFavorites()
        }catch (e: Exception){
            Log.e("FavoriteDatabase",e.message!!)
            null
        }
    }
}
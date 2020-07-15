package com.codose.vethr.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.codose.vethr.R
import com.pixplicity.easyprefs.library.Prefs
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

object Utils {
    val LOCATION = 0x1
    val GPS_SETTINGS = 0x7//GPS Setting

    val MY_PERMISSIONS_REQUEST_LOCATION = 99
    fun getWeatherDrawable(data: String) : Int{
        return when(data){
            "Clouds" -> R.raw.cloud
            "Clear" -> R.raw.sun
            "Snow" -> R.raw.snow
            "Rain" -> R.raw.rain
            "Drizzle" -> R.raw.rain
            "Thunderstorm" -> R.raw.rain
            else -> R.raw.mist
        }
    }

    fun setThemeMode(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        Prefs.putBoolean("themeMode", themeMode == AppCompatDelegate.MODE_NIGHT_YES)
    }

    fun longToDate(date:Long):String{
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val mDate = Date(date*1000)
        return sdf.format(mDate)
    }

    fun getTempString(temp: Double): String {
        return "${temp}°C"
    }

    fun askForPermission(context: Context, permission: String = Manifest.permission.ACCESS_FINE_LOCATION, requestCode: Int = Utils.LOCATION): Boolean {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)) {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again


                ActivityCompat.requestPermissions(context, arrayOf(permission), requestCode!!)
                Prefs.putBoolean("permission",true)

            } else {
                Prefs.putBoolean("permission",true)
                ActivityCompat.requestPermissions(context, arrayOf(permission), requestCode!!)
            }
            return false


        } else {
            //Toast.makeText(context, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show()
            return true
        }
    }
}
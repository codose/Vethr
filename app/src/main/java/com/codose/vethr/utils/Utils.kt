package com.codose.vethr.utils

import androidx.appcompat.app.AppCompatDelegate
import com.codose.vethr.R
import com.pixplicity.easyprefs.library.Prefs

object Utils {
    fun getWeatherDrawable(data: String) : Int{
        return when(data){
            "Clouds" -> R.drawable.ic_home

            

            else -> R.drawable.ic_logo
        }
    }

    fun setThemeMode(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        Prefs.putBoolean("themeMode", themeMode == AppCompatDelegate.MODE_NIGHT_YES)
    }
}
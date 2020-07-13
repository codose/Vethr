package com.codose.vethr.utils

import com.codose.vethr.R

object Utils {
    fun getWeatherDrawable(data: String) : Int{
        return when(data){
            "Clouds" -> R.drawable.ic_home

            

            else -> R.drawable.ic_logo
        }
    }
}
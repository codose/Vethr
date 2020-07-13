package com.codose.vethr.network.response.searchResponse


import com.google.gson.annotations.SerializedName

data class Acces(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)
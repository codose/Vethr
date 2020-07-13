package com.codose.vethr.network.response.searchResponse


import com.google.gson.annotations.SerializedName

data class Position(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)
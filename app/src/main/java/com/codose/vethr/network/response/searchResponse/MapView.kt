package com.codose.vethr.network.response.searchResponse


import com.google.gson.annotations.SerializedName

data class MapView(
    @SerializedName("east")
    val east: Double,
    @SerializedName("north")
    val north: Double,
    @SerializedName("south")
    val south: Double,
    @SerializedName("west")
    val west: Double
)
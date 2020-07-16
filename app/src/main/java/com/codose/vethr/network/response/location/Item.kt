package com.codose.vethr.network.response.location


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("address")
    val address: Address,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("localityType")
    val localityType: String,
    @SerializedName("mapView")
    val mapView: MapView,
    @SerializedName("position")
    val position: Position,
    @SerializedName("resultType")
    val resultType: String,
    @SerializedName("title")
    val title: String
)
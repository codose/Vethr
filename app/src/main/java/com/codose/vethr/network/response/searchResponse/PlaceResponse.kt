package com.codose.vethr.network.response.searchResponse


import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("items")
    val items: List<Item>
)
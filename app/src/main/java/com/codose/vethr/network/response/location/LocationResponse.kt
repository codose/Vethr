package com.codose.vethr.network.response.location


import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("items")
    val items: List<Item>
)
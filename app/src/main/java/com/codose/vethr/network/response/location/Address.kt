package com.codose.vethr.network.response.location


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("city")
    val city: String,
    @SerializedName("countryCode")
    val countryCode: String,
    @SerializedName("countryName")
    val countryName: String,
    @SerializedName("county")
    val county: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("state")
    val state: String
)
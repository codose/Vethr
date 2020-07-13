package com.codose.vethr.network.response.searchResponse


import com.google.gson.annotations.SerializedName

data class Phone(
    @SerializedName("value")
    val value: String
)
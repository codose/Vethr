package com.codose.vethr.network.response.searchResponse


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("primary")
    val primary: Boolean
)
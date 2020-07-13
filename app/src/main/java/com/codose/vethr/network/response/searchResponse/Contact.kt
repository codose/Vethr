package com.codose.vethr.network.response.searchResponse


import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("phone")
    val phone: List<Phone>
)
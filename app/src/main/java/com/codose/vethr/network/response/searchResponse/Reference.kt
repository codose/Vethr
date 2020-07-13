package com.codose.vethr.network.response.searchResponse


import com.google.gson.annotations.SerializedName

data class Reference(
    @SerializedName("id")
    val id: String,
    @SerializedName("supplier")
    val supplier: Supplier
)
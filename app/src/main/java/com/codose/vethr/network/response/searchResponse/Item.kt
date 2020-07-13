package com.codose.vethr.network.response.searchResponse


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("access")
    val access: List<Acces>,
    @SerializedName("address")
    val address: Address,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("contacts")
    val contacts: List<Contact>,
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
    @SerializedName("references")
    val references: List<Reference>,
    @SerializedName("resultType")
    val resultType: String,
    @SerializedName("title")
    val title: String
)
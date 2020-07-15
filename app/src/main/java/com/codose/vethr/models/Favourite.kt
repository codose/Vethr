package com.codose.vethr.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favourite_table")
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    @ColumnInfo(name = "location_name")
    val location: String = "",
    @ColumnInfo(name = "longitude")
    val long: Double = 0.0,
    @ColumnInfo(name = "latitude")
    val lat: Double = 0.0
)


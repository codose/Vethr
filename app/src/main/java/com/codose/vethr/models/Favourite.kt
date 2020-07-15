package com.codose.vethr.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favourite_table")
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    @ColumnInfo(name = "location_name")
    var location: String = "",
    @ColumnInfo(name = "longitude")
    var long: Double = 0.0,
    @ColumnInfo(name = "latitude")
    var lat: Double = 0.0
)


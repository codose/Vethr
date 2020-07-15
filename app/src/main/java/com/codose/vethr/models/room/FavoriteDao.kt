package com.codose.vethr.models.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.codose.vethr.models.Favourite

@Dao
interface FavoriteDao {
    @Insert
    fun insert(favourite: Favourite)

    @Delete
    fun delete(favourite: Favourite)

    @Query("SELECT * FROM favourite_table ORDER BY id DESC")
    fun getAllFavorites(): LiveData<List<Favourite>>
}
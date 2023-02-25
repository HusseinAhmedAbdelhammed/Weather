package com.example.data.local

import androidx.room.*

@Dao
interface FavDao {
    @Query("Select * from fav")
    fun getAllFav():List<FavEntity>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFav(favEntity: FavEntity)
    @Delete
    fun deleteFav(favEntity: FavEntity)
}
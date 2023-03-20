package com.example.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDao {
    @Query("Select * from fav")
    fun getAllFav(): Flow<List<FavEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFav(favEntity: FavEntity)
    @Delete
    fun deleteFav(favEntity: FavEntity)
}
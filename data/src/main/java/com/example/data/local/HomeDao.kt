package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface HomeDao {
    @Query("Select * from home")
    fun getHome(): Flow<List<Home>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHome(home: Home)
}
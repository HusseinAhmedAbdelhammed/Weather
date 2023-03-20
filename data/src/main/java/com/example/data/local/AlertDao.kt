package com.example.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Dao
interface AlertDao {
    @Query("Select * from alert")
    fun getAllAlerts(): Flow<List<AlertEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAlert(alertEntity: AlertEntity)
    @Delete
    fun deleteAlert(alertEntity: AlertEntity)
}
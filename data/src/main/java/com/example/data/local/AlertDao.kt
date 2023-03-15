package com.example.data.local

import androidx.room.*

@Dao
interface AlertDao {
    @Query("Select * from alert")
    fun getAllAlerts():List<AlertEntity>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAlert(alertEntity: AlertEntity)
    @Delete
    fun deleteAlert(alertEntity: AlertEntity)
}
package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert")
data class AlertEntity(@PrimaryKey(autoGenerate = true) var id: Int? = null,var startTime:Long,var endTime:Long,
var option:String)

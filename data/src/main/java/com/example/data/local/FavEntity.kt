package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav")
data class FavEntity(@PrimaryKey val city:String, val lat:Double, val lon:Double)

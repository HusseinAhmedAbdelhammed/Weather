package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home")
data class Home(@PrimaryKey val id:Int, val temp:Double, val name:String, val pressure:Int, val humidity:Int, val visibility:Int, val all:Int, val icon:String)


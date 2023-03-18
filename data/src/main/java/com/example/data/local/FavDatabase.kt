package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavEntity::class,Home::class,AlertEntity::class], version = 5)
abstract class FavDatabase :RoomDatabase(){
    abstract fun favDao():FavDao
    abstract fun homeDao():HomeDao
    abstract fun alertDao():AlertDao
}
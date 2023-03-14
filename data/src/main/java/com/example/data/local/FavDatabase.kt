package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavEntity::class,Home::class], version = 2)
abstract class FavDatabase :RoomDatabase(){
    abstract fun favDao():FavDao
    abstract fun homeDao():HomeDao
}
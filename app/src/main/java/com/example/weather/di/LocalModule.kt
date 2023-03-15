package com.example.weather.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.FavDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context)= Room.databaseBuilder(context,FavDatabase::class.java,"fav_db").fallbackToDestructiveMigration().build()
    @Provides
    @Singleton
    fun provideDao(db:FavDatabase)=db.favDao()
    @Provides
    @Singleton
    fun provideHomeDao(db:FavDatabase)=db.homeDao()
    @Provides
    @Singleton
    fun provideAlertDao(db: FavDatabase)=db.alertDao()
}
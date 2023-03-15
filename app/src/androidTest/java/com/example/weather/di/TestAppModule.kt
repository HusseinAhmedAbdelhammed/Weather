
package com.example.weather.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.data.local.FavDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Named("test_db")
    fun provideInMemoryDatabase(@ApplicationContext context:Context){
        Room.inMemoryDatabaseBuilder(context,FavDatabase::class.java).allowMainThreadQueries().build()
    }
}
package com.example.weather.di

import android.content.Context
import com.example.weather.helpers.NetworkConnectivityChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkConnectivityModule {
    @Singleton
    @Provides
    fun provideChecker(@ApplicationContext context: Context):NetworkConnectivityChecker{
        return NetworkConnectivityChecker(context)
    }
}
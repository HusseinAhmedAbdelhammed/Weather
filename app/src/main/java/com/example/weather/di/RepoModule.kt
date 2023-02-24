package com.example.weather.di

import com.example.data.remote.ApiService
import com.example.data.repo.WeatherRepoImplementation
import com.example.domain.repo.WeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun provideRepo(apiService: ApiService):WeatherRepo{
        return WeatherRepoImplementation(apiService)
    }
}
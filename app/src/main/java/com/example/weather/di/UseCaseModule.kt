package com.example.weather.di

import com.example.domain.repo.WeatherRepo
import com.example.domain.usecase.GetForcast
import com.example.domain.usecase.GetWeather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    
    @Provides
    fun provideUseCaseWeath(weatherRepo: WeatherRepo,lat:Double,lon:Double,unit:String,lang:String,apiKey:String):GetWeather{
        return GetWeather(weatherRepo,lat,lon,unit,lang,apiKey)
    }
    @Provides
    fun provideUseCaseForeCast(weatherRepo: WeatherRepo,lat: Double,lon: Double,apiKey: String):GetForcast{
        return GetForcast(weatherRepo,lat,lon,apiKey)
    }
}
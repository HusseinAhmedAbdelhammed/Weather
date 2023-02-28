package com.example.weather.di

import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.repo.WeatherRepo
import com.example.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCaseWeath(weatherRepo: WeatherRepo):GetWeather{
        return GetWeather(weatherRepo)
    }
    @Provides
    fun provideUseCaseForeCast(weatherRepo: WeatherRepo):GetForcast{
        return GetForcast(weatherRepo)
    }
    @Provides
    fun provideUseCaseGetFav(weatherRepo: WeatherRepo):GetFav{
        return GetFav(weatherRepo)
    }
    @Provides
    fun provideUseCaseAddFav(weatherRepo: WeatherRepo):AddFav{
        return AddFav(weatherRepo)
    }
    @Provides
    fun provideUseCaseDelFav(weatherRepo: WeatherRepo):DelFav{
        return DelFav(weatherRepo)
    }
    @Provides
    fun provideUseCaseAddHome(weatherRepo: WeatherRepo):AddHome{
        return AddHome(weatherRepo)
    }
    @Provides
    fun provideUseCaseGetHome(weatherRepo: WeatherRepo):GetHome{
        return GetHome(weatherRepo)
    }
}
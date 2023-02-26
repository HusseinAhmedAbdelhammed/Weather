package com.example.domain.repo

import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.entity.forcast.ForcastResponse
import com.example.domain.entity.weather.WeatherResponse


interface WeatherRepo {
    fun getWeatherFromRemote(lat:Double,lon:Double,lang:String,apiKey:String): WeatherResponse
    fun getForcastFromRemote(lat:Double,lon:Double,apiKey:String):ForcastResponse
    fun getFavFromLocal():List<FavDomainEntity>
    fun addFav(fav:FavDomainEntity)
    fun deleteFav(fav:FavDomainEntity)
}
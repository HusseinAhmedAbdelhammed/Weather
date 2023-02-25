package com.example.domain.repo

import com.example.domain.entity.forcast.ForcastResponse
import com.example.domain.entity.weather.WeatherResponse


interface WeatherRepo {
    fun getWeatherFromRemote(lat:Double,lon:Double,units:String,lang:String,apiKey:String): WeatherResponse
    fun getForcastFromRemote(lat:Double,lon:Double,apiKey:String):ForcastResponse
}
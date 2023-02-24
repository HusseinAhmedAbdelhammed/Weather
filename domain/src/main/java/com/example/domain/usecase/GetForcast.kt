package com.example.domain.usecase

import com.example.domain.repo.WeatherRepo

class GetForcast(private val weatherRepo: WeatherRepo,private val lat:Double,private val lon:Double,private val apiKey:String) {
    suspend operator fun invoke()=weatherRepo.getForcastFromRemote(lat,lon,apiKey)
}
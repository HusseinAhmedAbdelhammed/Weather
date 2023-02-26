package com.example.domain.usecase

import com.example.domain.repo.WeatherRepo

class GetForcast(private val weatherRepo: WeatherRepo) {
    suspend operator fun invoke(lat:Double,lon:Double,apiKey:String)=weatherRepo.getForcastFromRemote(lat,lon,apiKey)
}
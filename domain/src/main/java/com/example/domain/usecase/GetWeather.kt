package com.example.domain.usecase

import com.example.domain.repo.WeatherRepo

class GetWeather(private val weatherRepo: WeatherRepo,private val lat:Double,private val lon:Double,private val units:String,
                 private val lang:String,private val apiKey:String) {
   suspend operator fun invoke()=weatherRepo.getWeatherFromRemote(lat, lon , units,lang, apiKey)
}
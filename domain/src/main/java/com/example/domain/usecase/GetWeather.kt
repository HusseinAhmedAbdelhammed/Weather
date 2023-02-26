package com.example.domain.usecase

import com.example.domain.repo.WeatherRepo

class GetWeather(private val weatherRepo: WeatherRepo) {
   suspend operator fun invoke(lat:Double,lon:Double,lang:String,apiKey:String)=weatherRepo.getWeatherFromRemote(lat, lon , lang, apiKey)
}
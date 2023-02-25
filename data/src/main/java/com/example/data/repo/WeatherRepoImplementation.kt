package com.example.data.repo

import com.example.data.remote.ApiService
import com.example.domain.entity.forcast.ForcastResponse
import com.example.domain.entity.weather.WeatherResponse
import com.example.domain.repo.WeatherRepo

class WeatherRepoImplementation(private val apiService: ApiService):WeatherRepo {
    override fun getWeatherFromRemote(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
        apiKey: String
    ): WeatherResponse =apiService.getWeather(lat,lon,units,lang,apiKey)

    override fun getForcastFromRemote(lat: Double, lon: Double, apiKey: String): ForcastResponse = apiService.getForcast(lat,lon,apiKey)

}
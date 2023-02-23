package com.example.domain.usecase

import com.example.domain.repo.WeatherRepo

class GetWeather(private val weatherRepo: WeatherRepo) {
    operator fun invoke()=weatherRepo.getWeatherFromRemote()
}
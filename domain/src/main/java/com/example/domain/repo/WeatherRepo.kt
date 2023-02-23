package com.example.domain.repo

import com.example.domain.entity.WeatherResponse

interface WeatherRepo {
    fun getWeatherFromRemote():WeatherResponse
}
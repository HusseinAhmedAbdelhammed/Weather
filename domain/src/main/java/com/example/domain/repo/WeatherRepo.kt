package com.example.domain.repo

import com.example.domain.entity.onecall.WeatherResponse

interface WeatherRepo {
    fun getWeatherFromRemote(): WeatherResponse
}
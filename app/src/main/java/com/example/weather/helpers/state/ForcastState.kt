package com.example.weather.helpers.state

import com.example.domain.entity.forcast.ForcastResponse
import com.example.domain.entity.weather.WeatherResponse

sealed class ForcastState{
    class Success(val data: ForcastResponse):ForcastState()
    class Failure(val error:Throwable):ForcastState()
    object Loading : ForcastState()
}

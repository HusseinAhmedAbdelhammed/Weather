package com.example.weather.helpers.state

import com.example.domain.entity.weather.WeatherResponse

sealed class ApiState{
    class Success(val data:WeatherResponse):ApiState()
    class Failure(val error:Throwable):ApiState()
    object Loading : ApiState()

}

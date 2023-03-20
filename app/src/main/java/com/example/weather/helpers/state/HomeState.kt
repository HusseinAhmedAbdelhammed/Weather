package com.example.weather.helpers.state

import com.example.domain.entity.fakeentity.HomeFake
import com.example.domain.entity.forcast.ForcastResponse

sealed class HomeState{
    class Success(val data: List<HomeFake>):HomeState()
    class Failure(val error:Throwable):HomeState()
    object Loading : HomeState()
}

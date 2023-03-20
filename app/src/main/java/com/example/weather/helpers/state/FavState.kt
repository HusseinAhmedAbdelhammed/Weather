package com.example.weather.helpers.state

import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.entity.forcast.ForcastResponse

sealed class FavState{
    class Success(val data: List<FavDomainEntity>):FavState()
    class Failure(val error:Throwable):FavState()
    object Loading : FavState()
}

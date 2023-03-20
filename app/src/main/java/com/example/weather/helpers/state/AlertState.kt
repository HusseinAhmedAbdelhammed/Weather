package com.example.weather.helpers.state

import com.example.domain.entity.fakeentity.AlertDomainEntity
import com.example.domain.entity.fakeentity.HomeFake

sealed class AlertState{
    class Success(val data: List<AlertDomainEntity>):AlertState()
    class Failure(val error:Throwable):AlertState()
    object Loading : AlertState()
}

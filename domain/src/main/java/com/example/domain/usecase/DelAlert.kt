package com.example.domain.usecase

import com.example.domain.entity.fakeentity.AlertDomainEntity
import com.example.domain.repo.WeatherRepo

class DelAlert(private val weatherRepo: WeatherRepo) {
    suspend operator fun invoke(alert: AlertDomainEntity)=weatherRepo.deleteAlert(alert)
}
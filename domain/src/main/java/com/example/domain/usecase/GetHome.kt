package com.example.domain.usecase

import com.example.domain.repo.WeatherRepo

class GetHome(private val weatherRepo: WeatherRepo) {
    suspend operator fun invoke()=weatherRepo.getHomeFromLocal()
}
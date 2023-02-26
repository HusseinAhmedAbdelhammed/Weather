package com.example.domain.usecase

import com.example.domain.repo.WeatherRepo

class GetFav (private val weatherRepo: WeatherRepo){
    suspend operator fun invoke()=weatherRepo.getFavFromLocal()
}
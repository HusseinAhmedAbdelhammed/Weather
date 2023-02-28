package com.example.domain.usecase

import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.entity.fakeentity.HomeFake
import com.example.domain.repo.WeatherRepo

class AddHome(private val weatherRepo: WeatherRepo) {
    suspend operator fun invoke(homeFake: HomeFake)=weatherRepo.addHome(homeFake)

}
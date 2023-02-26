package com.example.domain.usecase

import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.repo.WeatherRepo

class DelFav(private val weatherRepo: WeatherRepo, private val fav: FavDomainEntity) {
    suspend operator fun invoke()=weatherRepo.deleteFav(fav)
}
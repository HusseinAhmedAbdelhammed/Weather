package com.example.domain.usecase

import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.repo.WeatherRepo

class DelFav(private val weatherRepo: WeatherRepo) {
    suspend operator fun invoke(fav: FavDomainEntity)=weatherRepo.deleteFav(fav)
}
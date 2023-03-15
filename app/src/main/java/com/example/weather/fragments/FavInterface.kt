package com.example.weather.fragments

import com.example.domain.entity.fakeentity.FavDomainEntity

interface FavInterface {
    fun delete(fav:FavDomainEntity)
    fun goToDetails(fav:FavDomainEntity)
}
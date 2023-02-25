package com.example.data.repo

import com.example.data.local.FavDatabase
import com.example.data.local.FavEntity
import com.example.data.mappers.FavMapper
import com.example.data.remote.ApiService
import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.entity.forcast.ForcastResponse
import com.example.domain.entity.weather.WeatherResponse
import com.example.domain.repo.WeatherRepo

class WeatherRepoImplementation(private val apiService: ApiService,private val favDB:FavDatabase):WeatherRepo {
    override fun getWeatherFromRemote(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
        apiKey: String
    ): WeatherResponse =apiService.getWeather(lat,lon,units,lang,apiKey)

    override fun getForcastFromRemote(lat: Double, lon: Double, apiKey: String): ForcastResponse = apiService.getForcast(lat,lon,apiKey)
    override fun getFavFromLocal(): List<FavDomainEntity> {
       return FavMapper.mapListFromEntity(favDB.favDao().getAllFav())
    }

    override fun addFav(fav: FavDomainEntity) {
        favDB.favDao().addFav(FavMapper.mapToEntity(fav))
    }

    override fun deleteFav(fav: FavDomainEntity) {
       favDB.favDao().deleteFav(FavMapper.mapToEntity(fav))
    }

}
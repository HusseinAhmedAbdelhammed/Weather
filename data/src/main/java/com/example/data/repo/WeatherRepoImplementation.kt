package com.example.data.repo

import com.example.data.local.FavDatabase
import com.example.data.local.FavEntity
import com.example.data.mappers.FavMapper
import com.example.data.mappers.HomeMapper
import com.example.data.remote.ApiService
import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.entity.fakeentity.HomeFake
import com.example.domain.entity.forcast.ForcastResponse
import com.example.domain.entity.weather.WeatherResponse
import com.example.domain.repo.WeatherRepo

class WeatherRepoImplementation(private val apiService: ApiService,private val favDB:FavDatabase):WeatherRepo {
    override suspend fun getWeatherFromRemote(
        lat: Double,
        lon: Double,
        lang: String,
        apiKey: String
    ): WeatherResponse =apiService.getWeather(lat,lon,lang,apiKey)

    override suspend fun getForcastFromRemote(lat: Double, lon: Double, apiKey: String): ForcastResponse = apiService.getForcast(lat,lon,apiKey)
    override suspend fun getFavFromLocal(): List<FavDomainEntity> {
       return FavMapper.mapListFromEntity(favDB.favDao().getAllFav())
    }

    override suspend fun addFav(fav: FavDomainEntity) {
        favDB.favDao().addFav(FavMapper.mapToEntity(fav))
    }

    override suspend fun deleteFav(fav: FavDomainEntity) {
       favDB.favDao().deleteFav(FavMapper.mapToEntity(fav))
    }

    override suspend fun getHomeFromLocal(): List<HomeFake> {
        return HomeMapper.mapListFromEntity(favDB.homeDao().getHome())
    }

    override suspend fun addHome(homeFake: HomeFake) {
        favDB.homeDao().addHome(HomeMapper.mapToEntity(homeFake))
    }

}
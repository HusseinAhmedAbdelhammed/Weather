package com.example.domain.repo

import com.example.domain.entity.fakeentity.AlertDomainEntity
import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.entity.fakeentity.HomeFake
import com.example.domain.entity.forcast.ForcastResponse
import com.example.domain.entity.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow


interface WeatherRepo {
    suspend fun getWeatherFromRemote(lat:Double,lon:Double,lang:String,apiKey:String): WeatherResponse
    suspend fun getForcastFromRemote(lat:Double,lon:Double,apiKey:String):ForcastResponse
    suspend fun getFavFromLocal(): Flow<List<FavDomainEntity>>
    suspend fun addFav(fav:FavDomainEntity)
    suspend fun deleteFav(fav:FavDomainEntity)
    suspend fun getHomeFromLocal():Flow<List<HomeFake>>
    suspend fun addHome(homeFake: HomeFake)
    suspend fun addAlert(alertDomainEntity: AlertDomainEntity)
    suspend fun getAlert():Flow<List<AlertDomainEntity>>
    suspend fun deleteAlert(alertDomainEntity: AlertDomainEntity)

}
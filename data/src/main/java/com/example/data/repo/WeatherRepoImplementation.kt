package com.example.data.repo

import android.util.Log
import com.example.data.local.AlertEntity
import com.example.data.local.FavDatabase
import com.example.data.local.FavEntity
import com.example.data.local.Home
import com.example.data.mappers.AlertMapper
import com.example.data.mappers.FavMapper
import com.example.data.mappers.HomeMapper
import com.example.data.remote.ApiService
import com.example.domain.entity.fakeentity.AlertDomainEntity
import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.entity.fakeentity.HomeFake
import com.example.domain.entity.forcast.ForcastResponse
import com.example.domain.entity.weather.WeatherResponse
import com.example.domain.repo.WeatherRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class WeatherRepoImplementation(private val apiService: ApiService,private val favDB:FavDatabase):WeatherRepo {
    override suspend fun getWeatherFromRemote(
        lat: Double,
        lon: Double,
        lang: String,
        apiKey: String
    ): WeatherResponse =apiService.getWeather(lat,lon,lang,apiKey)

    override suspend fun getForcastFromRemote(lat: Double, lon: Double, apiKey: String): ForcastResponse = apiService.getForcast(lat,lon,apiKey)
    ////////////////////////////////////////////////////////////////
    override suspend fun getFavFromLocal(): Flow<List<FavDomainEntity>> = favDB.favDao().getAllFav().map{FavMapper.mapListFromEntity(it)}
//////////////////////////////////////////////////////////////////
    override suspend fun addFav(fav: FavDomainEntity) {
        favDB.favDao().addFav(FavMapper.mapToEntity(fav))
    }

    override suspend fun deleteFav(fav: FavDomainEntity) {
       favDB.favDao().deleteFav(FavMapper.mapToEntity(fav))
    }

    override suspend fun getHomeFromLocal(): Flow<List<HomeFake>> = favDB.homeDao().getHome().map{HomeMapper.mapListFromEntity(it)}

    override suspend fun addHome(homeFake: HomeFake) {
        favDB.homeDao().addHome(HomeMapper.mapToEntity(homeFake))
    }

    override suspend fun addAlert(alertDomainEntity: AlertDomainEntity) {
        favDB.alertDao().addAlert(AlertMapper.mapToEntity(alertDomainEntity))
    }

    override suspend fun getAlert(): Flow<List<AlertDomainEntity>> = favDB.alertDao().getAllAlerts().map{AlertMapper.mapListFromEntity(it)}

    override suspend fun deleteAlert(alertDomainEntity: AlertDomainEntity) {
        favDB.alertDao().deleteAlert(AlertMapper.mapToEntity(alertDomainEntity))
    }

}
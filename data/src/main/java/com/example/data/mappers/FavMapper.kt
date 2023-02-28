package com.example.data.mappers

import com.example.data.local.FavEntity
import com.example.domain.entity.fakeentity.FavDomainEntity

object FavMapper {
    fun mapToEntity(favDomainEntity: FavDomainEntity):FavEntity{
        return FavEntity(favDomainEntity.city,favDomainEntity.lat,favDomainEntity.lon)
    }
    fun mapFromEntity(favEntity: FavEntity):FavDomainEntity{
        return FavDomainEntity(favEntity.city,favEntity.lat,favEntity.lon)
    }
    fun mapListToEntity(favDomainEntity: List<FavDomainEntity>):List<FavEntity>{
        var list= mutableListOf<FavEntity>()
        for(i in 0 .. favDomainEntity.size-1){
            list.add(FavEntity(favDomainEntity.get(i).city,favDomainEntity.get(i).lat,favDomainEntity.get(i).lon))
        }
        return list
    }
    fun mapListFromEntity(favEntity: List<FavEntity>):List<FavDomainEntity>{
        var list= mutableListOf<FavDomainEntity>()
        for(i in 0 .. favEntity.size-1){
            list.add(FavDomainEntity(favEntity.get(i).city,favEntity.get(i).lat,favEntity.get(i).lon))
        }
        return list
    }
}
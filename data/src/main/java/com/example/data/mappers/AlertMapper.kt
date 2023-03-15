package com.example.data.mappers

import com.example.data.local.AlertEntity
import com.example.data.local.FavEntity
import com.example.domain.entity.fakeentity.AlertDomainEntity
import com.example.domain.entity.fakeentity.FavDomainEntity

object AlertMapper {
    fun mapToEntity(alertDomainEntity: AlertDomainEntity): AlertEntity {
        return AlertEntity(null,alertDomainEntity.startTime,alertDomainEntity.endTime
        ,alertDomainEntity.option)
    }
    fun mapFromEntity(alertEntity: AlertEntity): AlertDomainEntity {
        return AlertDomainEntity(alertEntity.startTime,alertEntity.endTime,
        alertEntity.option)
    }
    fun mapListToEntity(alertDomainEntity: List<AlertDomainEntity>):List<AlertEntity>{
        var list= mutableListOf<AlertEntity>()
        for(i in 0 .. alertDomainEntity.size-1){
            list.add(AlertEntity(null,alertDomainEntity.get(i).startTime,alertDomainEntity.get(i).endTime,
                alertDomainEntity.get(i).option))
        }
        return list
    }
    fun mapListFromEntity(alertEntity: List<AlertEntity>):List<AlertDomainEntity>{
        var list= mutableListOf<AlertDomainEntity>()
        for(i in 0 .. alertEntity.size-1){
            list.add(AlertDomainEntity(alertEntity.get(i).startTime,alertEntity.get(i).endTime,
                alertEntity.get(i).option))
        }
        return list
    }
}
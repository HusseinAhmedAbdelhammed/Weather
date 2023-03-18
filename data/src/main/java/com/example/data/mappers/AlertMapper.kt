package com.example.data.mappers

import com.example.data.local.AlertEntity
import com.example.data.local.FavEntity
import com.example.domain.entity.fakeentity.AlertDomainEntity
import com.example.domain.entity.fakeentity.FavDomainEntity

object AlertMapper {
    fun alertDCompiner(alertD:AlertDomainEntity):String{
        return alertD.option+alertD.startTime.toString()+alertD.endTime.toString()
    }
    fun alertECompiner(alertE:AlertEntity):String{
        return alertE.option+alertE.startTime.toString()+alertE.endTime.toString()
    }
    fun mapToEntity(alertDomainEntity: AlertDomainEntity): AlertEntity {
        return AlertEntity(
            alertDCompiner(alertDomainEntity),alertDomainEntity.startTime,alertDomainEntity.endTime
        ,alertDomainEntity.option)
    }
    fun mapFromEntity(alertEntity: AlertEntity): AlertDomainEntity {
        return AlertDomainEntity(
            alertECompiner(alertEntity),alertEntity.startTime,alertEntity.endTime,
        alertEntity.option)
    }
    fun mapListToEntity(alertDomainEntity: List<AlertDomainEntity>):List<AlertEntity>{
        var list= mutableListOf<AlertEntity>()
        for(i in 0 .. alertDomainEntity.size-1){
            list.add(AlertEntity(
                alertDCompiner(alertDomainEntity.get(i)),alertDomainEntity.get(i).startTime,alertDomainEntity.get(i).endTime,
                alertDomainEntity.get(i).option))
        }
        return list
    }
    fun mapListFromEntity(alertEntity: List<AlertEntity>):List<AlertDomainEntity>{
        var list= mutableListOf<AlertDomainEntity>()
        for(i in 0 .. alertEntity.size-1){
            list.add(AlertDomainEntity(
                alertECompiner(alertEntity.get(i)),alertEntity.get(i).startTime,alertEntity.get(i).endTime,
                alertEntity.get(i).option))
        }
        return list
    }
}
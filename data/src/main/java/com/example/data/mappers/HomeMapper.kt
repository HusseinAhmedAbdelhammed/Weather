package com.example.data.mappers

import com.example.data.local.Home
import com.example.domain.entity.fakeentity.HomeFake

object HomeMapper {
    fun mapToEntity(fake: HomeFake):Home{
        return Home(fake.id,fake.temp,fake.name,fake.pressure,fake.humidity,fake.visibility,fake.all,fake.icon)
    }
    fun mapFromEntity(home: Home):HomeFake{
        return HomeFake(home.id,home.temp,home.name,home.pressure,home.humidity,home.visibility,home.all,home.icon)
    }
    fun mapListToEntity(list:List<HomeFake>):List<Home>{
        var homeList = mutableListOf<Home>()
        for(i in list.indices){
            homeList.add(Home(list.get(i).id,list.get(i).temp,list.get(i).name,
                list.get(i).pressure,list.get(i).humidity,list.get(i).visibility,list.get(i).all,list.get(i).icon))
        }
        return homeList
    }
    fun mapListFromEntity(list:List<Home>):List<HomeFake>{
        var fakeList= mutableListOf<HomeFake>()
        for(i in list.indices){
            fakeList.add(HomeFake(list.get(i).id,list.get(i).temp,list.get(i).name,
                list.get(i).pressure,list.get(i).humidity,list.get(i).visibility,list.get(i).all,list.get(i).icon))
        }
        return fakeList
    }
}
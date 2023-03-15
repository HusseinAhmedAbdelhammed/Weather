package com.example.weather.helpers

object Navigator {
    var dir=""
    var myArrival=""
    var hasEntered=""
    var hasDetails=""
    fun moveFromTestToMap(){
        dir=Consts.DIRECTION_SET
    }
    fun moveFromFavToMap(){
        dir=Consts.DIRECTION_FAV
    }
    fun fromMapToFav(){
        myArrival=Consts.FROM_MAP_TO_FAV
    }
    fun fromMapToTest(){
        myArrival=Consts.FROM_MAP_TO_TEST
    }
}
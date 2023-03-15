package com.example.weather.helpers

import com.example.domain.entity.forcast.ForcastResponse
import java.text.SimpleDateFormat
import java.util.*

object WeatherHelper {
    var desc=""

    fun setDescr(descr:String){
        desc=descr
    }
    fun getDescr():String{
        return desc
    }
    fun getDateTime(dt: Int,language:String): String {
        val formatter = SimpleDateFormat("MMM d", Locale(language))
        formatter.timeZone = TimeZone.getTimeZone("GMT+2")
        return formatter.format(Date(dt * 1000L))
    }
    fun getDate(dt:Int):String{
        val formatter=SimpleDateFormat("hh a")
        formatter.timeZone = TimeZone.getTimeZone("GMT+2")
        return formatter.format(Date(dt * 1000L))
    }
    fun getDay(num1:String,num2:String):Int{
        var day:Int=0
        if(num1.equals("0")){
            day=Integer.valueOf(num2)
        }else{
            day=Integer.valueOf(num1+num2)
        }
        return day
    }
    fun getHourly(forcastResponse: ForcastResponse):List<String>{
        var currnDay:Int=0
        var hour=""
        var list= mutableListOf<String>()
        currnDay=WeatherHelper.getDay(forcastResponse.list.get(0).dt_txt.get(8).toString(),forcastResponse.list.get(0).dt_txt.get(9).toString())
        for(i in 0..forcastResponse.list.size-1){
            var newDay=WeatherHelper.getDay(forcastResponse.list.get(i).dt_txt.get(8).toString(),forcastResponse.list.get(i).dt_txt.get(9).toString())
            if(newDay==currnDay){
                hour=forcastResponse.list.get(i).main.temp_min.toString()+Consts.HOURLY_DELEMETER+forcastResponse.list.get(i).main.temp_max.toString()+
                        Consts.HOURLY_DELEMETER+forcastResponse.list.get(i).weather.get(0).icon+Consts.HOURLY_DELEMETER+
                        WeatherHelper.getDate(forcastResponse.list.get(i).dt)
                list.add(hour)
            }
        }
        return list
    }
    fun getDaily(forcastResponse: ForcastResponse,lang:String):List<String>{
        var currnDay:Int=0
        var daily=""
        var list= mutableListOf<String>()
        currnDay=WeatherHelper.getDay(forcastResponse.list.get(0).dt_txt.get(8).toString(),forcastResponse.list.get(0).dt_txt.get(9).toString())
        for(i in 0..forcastResponse.list.size-1){
            var newDay=WeatherHelper.getDay(forcastResponse.list.get(i).dt_txt.get(8).toString(),forcastResponse.list.get(i).dt_txt.get(9).toString())
            if(newDay!=currnDay){
                daily=forcastResponse.list.get(i).main.temp.toString()+
                        Consts.DAILY_DELEMETER+
                        WeatherHelper.getDateTime(forcastResponse.list.get(i).dt,lang)+Consts.DAILY_DELEMETER+
                        forcastResponse.list.get(i).weather.get(0).icon+Consts.DAILY_DELEMETER+
                        forcastResponse.list.get(i).weather.get(0).description
                list.add(daily)
                currnDay=WeatherHelper.getDay(forcastResponse.list.get(i).dt_txt.get(8).toString(),forcastResponse.list.get(i).dt_txt.get(9).toString())
            }
        }
        return list
    }
    fun compainObject(list:List<String>):String{
        var result = ""
        for(i in 0..list.size-1){
            if(i!=list.size-1){
                result+=list.get(i)+Consts.OBJECT_DELEMETER
            }else{
                result+=list.get(i)
            }

        }
        return result
    }
    fun fromCtoK(cel:Double):Double{
        return cel+ 273.15
    }
    fun fromKtoC(kel:Double):Double{
        return kel-273.15
    }
    fun fromCtoF(cel:Double):Double{
        return (cel*1.8)+32
    }
    fun fromFtoC(feh:Double):Double{
        return (feh*.5556)-32
    }
    fun fromKtoF(kel:Double):Double{
        return 1.8*(kel-273) + 32
    }
    fun fromFtoK(feh:Double):Double{
        return (feh + 459.67)*.5556
    }
    fun fromMStoMH(ms:Double):Double{
        return ms*2.23694
    }
    fun fromMHtoMS(mh:Double):Double{
        return mh*.447
    }

}
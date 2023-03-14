package com.example.weather.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.weather.helpers.Consts
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//if anything goes wrong use coroutine scope
@HiltViewModel
class SharedPrefViewModel @Inject constructor (private val shared:SharedPreferences):ViewModel() {
    fun windSpeedSetting(wind:String){
        shared.edit().putString(Consts.WIND_SETTINGS_KEY,wind).apply()
    }
    fun getWindSpeed():String{
       var res:String= shared.getString(Consts.WIND_SETTINGS_KEY,"")!!
       if (res==null){
           res=""
       }
        return res
    }
    fun tempSetting(temp:String){
        shared.edit().putString(Consts.TEMP_SETTINGS_KEY,temp).apply()

    }
    fun getTemp():String{
        var res:String= shared.getString(Consts.TEMP_SETTINGS_KEY,"")!!
        if (res==null){
            res=""
        }
        return res
    }
    fun locationSettings(location:String){
        shared.edit().putString(Consts.LOCATION_KEY,location).apply()
    }
    fun getLocation():String{
        var res:String= shared.getString(Consts.LOCATION_KEY,"")!!
        if (res==null){
            res=""
        }
        return res
    }
    fun languageSettings(lang:String){
        shared.edit().putString(Consts.LANG_KEY,lang).apply()
    }
    fun getLang():String{
        var res:String= shared.getString(Consts.LANG_KEY,"")!!
        if (res==null){
            res=""
        }
        return res
    }
    fun notificationSettings(not:String){
        shared.edit().putString(Consts.NOT_KEY,not).apply()
    }
    fun getNotification():String{
        var res:String= shared.getString(Consts.NOT_KEY,"")!!
        if (res==null){
            res=""
        }
        return res
    }
    fun notificationTypeSettings(notType:String){
        shared.edit().putString(Consts.NOT_TYPE_KEY,notType).apply()
    }
    fun getNotificationType():String{
        var res:String= shared.getString(Consts.NOT_TYPE_KEY,"")!!
        if (res==null){
            res=""
        }
        return res
    }

}
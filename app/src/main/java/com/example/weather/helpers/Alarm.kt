package com.example.weather.helpers

import android.app.Activity
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi

class Alarm(var activity:Activity) {
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    @RequiresApi(Build.VERSION_CODES.N)
    fun setAlarm(hour:Int, min:Int){
        alarmMgr = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(activity, AlarmReceiver::class.java).let { intent ->
            intent.putExtra(Consts.WETDESC,WeatherHelper.getDescr())
            PendingIntent.getBroadcast(activity, 0, intent,  0)
        }
        //PendingIntent.FLAG_UPDATE_CURRENT try it if 0 didin't work

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, min)
        }
        alarmMgr?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
    }
}
package com.example.weather.helpers

import java.text.SimpleDateFormat
import java.util.*

object WeatherHelper {
    fun getDateTime(dt: Int,language:String): String {
        val formatter = SimpleDateFormat("MMM d", Locale(language))
        formatter.timeZone = TimeZone.getTimeZone("GMT+2")
        return formatter.format(Date(dt * 1000L))
    }
}
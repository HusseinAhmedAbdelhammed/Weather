package com.example.weather.helpers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings

import com.google.android.gms.location.*

//const val PER_ID=505
//object LocationByGPS:Application() {
//
//    fun isLocationEnabled():Boolean{
//        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//    }
//    @SuppressLint("MissingPermission")
//    fun getLastLocation(permChecker:Boolean,requestPer:()->Unit){
//        if(permChecker){
//            if(isLocationEnabled()){
//                requestNewLocation()
//            }else{
//                val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
//            }
//        }else{
//            requestPer()
//        }
//    }
//    @SuppressLint("MissingPermission")
//    fun requestNewLocation(fus: FusedLocationProviderClient){
//        val locationRequest= LocationRequest()
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//        locationRequest.setInterval(0)
//
//        fus.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
//
//    }
//
////    val locationCallback: LocationCallback =object : LocationCallback(){
////        override fun onLocationResult(p0: LocationResult) {
////            val lastLocation: Location? = p0.getLastLocation()
////            lat.text=lastLocation.latitude.toString()
////            lon.text=lastLocation.longitude.toString()
////        }
////    }
//
//}
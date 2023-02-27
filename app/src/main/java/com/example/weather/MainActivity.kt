package com.example.weather

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.weather.viewmodels.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var testLat:TextView
    lateinit var testLon:TextView
    lateinit var testCity:TextView
    lateinit var fus:FusedLocationProviderClient
    private val viewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        viewModel.getWeather(33.44,94.04,"en","af7545a5decb1cf13fb3c5e0a6307a3a")
        lifecycleScope.launch {
            viewModel.weather.collect {
                testCity.text=it?.name
            }
        }
    }
    fun init(){
        testLat=findViewById(R.id.testLat)
        testLon=findViewById(R.id.testLon)
        testCity=findViewById(R.id.testCity)
        fus=LocationServices.getFusedLocationProviderClient(this)

    }
//    fun permChecker():Boolean{
//        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
//            Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED)
//    }
//    val requestPer=fun(){
//        ActivityCompat.requestPermissions(this, arrayOf<String>(
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION), PER_ID
//        )
//    }
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(requestCode== PER_ID){
//            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
//
//            }
//        }
//    }
}
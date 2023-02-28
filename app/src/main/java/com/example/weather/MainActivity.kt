package com.example.weather


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weather.viewmodels.FavViewModel
import com.example.weather.viewmodels.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var testLat:TextView
    lateinit var testLon:TextView
    lateinit var testCity:TextView
    lateinit var fus:FusedLocationProviderClient
    private val viewModel: WeatherViewModel by viewModels()
    private val viewModel2: FavViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        viewModel.getWeather(29.97371,32.52627,"en","af7545a5decb1cf13fb3c5e0a6307a3a")
        lifecycleScope.launch {
            viewModel.weather.collect {
                testCity.text=it?.name
            }
        }
//        viewModel2.addFavTODB(FavDomainEntity("Suez",33.44,94.04))
//
//        viewModel2.getFavList()
//        lifecycleScope.launch {
//            viewModel2.favList.collect {
//                testCity.text=it?.get(0)?.city
//            }
//        }


    }
    fun init(){
        testLat=findViewById(R.id.testLat)
        testLon=findViewById(R.id.testLon)
        testCity=findViewById(R.id.testCity)


    }

}
package com.example.weather.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.fakeentity.HomeFake
import com.example.domain.entity.forcast.ForcastResponse
import com.example.domain.entity.weather.WeatherResponse
import com.example.weather.R
import com.example.weather.adapters.DailyAdapter
import com.example.weather.adapters.HourlyAdapter
import com.example.weather.databinding.FragmentTestBinding
import com.example.weather.helpers.*
import com.example.weather.helpers.state.ApiState
import com.example.weather.helpers.state.ForcastState
import com.example.weather.helpers.state.HomeState
import com.example.weather.viewmodels.FavViewModel
import com.example.weather.viewmodels.HomeViewModel
import com.example.weather.viewmodels.SharedPrefViewModel
import com.example.weather.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestFragment : Fragment(),GPSFragmentInterface{
    lateinit var fakeHome:HomeFake
    private val locationPermissionCode = 2
    lateinit var binding:FragmentTestBinding
    private val viewModel: WeatherViewModel by viewModels()
    private val dbViewModel:FavViewModel by viewModels()
    private val homeViewModel:HomeViewModel by viewModels()
    private val sharedPrefViewModel:SharedPrefViewModel by viewModels()
    lateinit var dailyAdapter: DailyAdapter
    lateinit var hourlyAdapter: HourlyAdapter
    lateinit var forcastResponse: ForcastResponse
    lateinit var weatherResponse: WeatherResponse
    val locationByGPS=LocationByGPS(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentTestBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val network=NetworkConnectivityChecker(requireContext())
        if(!network.checkForInternet()){
            homeViewModel.getHomeList()
            lifecycleScope.launch {
                homeViewModel.homeList.collect{
                    when(it){
                    is HomeState.Success->{
                        binding.HomeprogressBar.visibility=View.GONE
                        binding.parentView.visibility=View.VISIBLE
                        fakeHome= it.data.get(0)!!
                        val listD=WeatherHelper.decompressObject(fakeHome.dailyList)
                        val listH=WeatherHelper.decompressObject(fakeHome.hourlyList)
                        binding.tvCity.text=fakeHome.name
                        when(sharedPrefViewModel.getTemp()){
                            Consts.TEMP_F->{binding.tvTemp.text=WeatherHelper.fromCtoF(fakeHome.temp).toString()
                                binding.tvHomeTempDisc.text="F"
                            }
                            Consts.TEMP_K->{binding.tvTemp.text=WeatherHelper.fromCtoK(fakeHome.temp).toString()
                                binding.tvHomeTempDisc.text="K"
                            }
                            else->{
                                binding.tvTemp.text=fakeHome.temp.toString()
                                binding.tvHomeTempDisc.text="C"
                            }
                        }
                        when(sharedPrefViewModel.getWindSpeed()){
                            Consts.WIND_MS->{binding.tvWindSpeed.text=fakeHome.windSpeed.toString()}
                            else->{
                                binding.tvWindSpeed.text=WeatherHelper.fromMStoMH(fakeHome.windSpeed).toString()
                            }
                        }
                        binding.tvHomeWeatherDescription.text=WeatherHelper.getDescr()
                        binding.tvPressure.text=fakeHome.pressure.toString()
                        binding.tvHumidity.text=fakeHome.humidity.toString()
                        binding.tvClouds.text=fakeHome.all.toString()
                        binding.tvUVI.text=fakeHome.temp.toString()
                        dailyAdapter= DailyAdapter(requireContext())
                        dailyAdapter.submitList(listD)
                        binding.recyclerViewDaily.apply {
                            adapter=dailyAdapter
                            layoutManager= LinearLayoutManager(context).apply {
                                orientation= RecyclerView.VERTICAL
                            }


                        }
                        hourlyAdapter= HourlyAdapter(requireContext())
                        hourlyAdapter.submitList(listH)
                        binding.recyclerViewHourly.apply {
                            adapter=hourlyAdapter
                            layoutManager= LinearLayoutManager(context).apply {
                                orientation= RecyclerView.HORIZONTAL
                            }
                        }
                        }
                    is HomeState.Loading->{
                        binding.parentView.visibility=View.GONE
                        binding.HomeprogressBar.visibility=View.VISIBLE

                        }

                    else ->{
                        Toast.makeText(requireContext(),"Data Failed",Toast.LENGTH_LONG).show()
                    }


                }}

            }

        }
        if(sharedPrefViewModel.getLocation()==""||sharedPrefViewModel.getLocation()==Consts.LOCATION_GPS){
            locationByGPS.getLocation(requireActivity())
        }
        if(sharedPrefViewModel.getLocation()==Consts.LOCATION_MAP&&Navigator.hasEntered!="dont"){
            Navigator.hasEntered="dont"
            Navigator.moveFromTestToMap()
            val action=TestFragmentDirections.actionTestFragmentToMapFragment()
            this.findNavController().navigate(action)
        }
        if(Navigator.myArrival==Consts.FROM_MAP_TO_TEST){
            Navigator.myArrival=""
            if(sharedPrefViewModel.getLang()==Consts.LANG_EN){
                viewModel.getWeather(TestFragmentArgs.fromBundle(requireArguments()).lat.toDouble()
                    ,TestFragmentArgs.fromBundle(requireArguments()).lon.toDouble(),"en",Consts.API_KEY)
            }else{
                viewModel.getWeather(TestFragmentArgs.fromBundle(requireArguments()).lat.toDouble()
                    ,TestFragmentArgs.fromBundle(requireArguments()).lon.toDouble(),"ar",Consts.API_KEY)
            }

            lifecycleScope.launch {
                viewModel.weather.collect{
                    when(it){
                        is ApiState.Success->{
                            binding.HomeprogressBar.visibility=View.GONE
                            binding.parentView.visibility=View.VISIBLE
                            cameFromMap(it.data)
                        }
                        is ApiState.Loading->{
                            binding.parentView.visibility=View.GONE
                            binding.HomeprogressBar.visibility=View.VISIBLE
                        }
                        else ->{
                            Toast.makeText(requireContext(),"Data Failed",Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }




    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
                locationByGPS.getLocation(requireActivity())

            }
            else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
                val intentd= Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                startActivity(intentd)

            }
        }
    }

    override fun setLatAndLon(lat: Double, lon: Double) {
        if(sharedPrefViewModel.getLang()==Consts.LANG_EN){
            viewModel.getWeather(lat,lon,"en",Consts.API_KEY)
        }else{
            viewModel.getWeather(lat,lon,"ar",Consts.API_KEY)
        }


        lifecycleScope.launch{
            viewModel.weather.collect{
                when(it){
                   is ApiState.Success->{
                       binding.HomeprogressBar.visibility=View.GONE
                       binding.parentView.visibility=View.VISIBLE
                        if(it.data.name!=null){
                            weatherResponse=it.data
                            WeatherHelper.setDescr(it.data.weather.get(0).description)
                            binding.tvCity.text=it.data.name
                        } else{
                            binding.tvCity.text="null city"
                        }
                        if(it.data.main.temp!=null){
                            when(sharedPrefViewModel.getTemp()){
                                Consts.TEMP_C->{binding.tvTemp.text=WeatherHelper.fromKtoC(it.data.main.temp).toInt().toString()
                                    binding.tvHomeTempDisc.text="C"
                                }
                                Consts.TEMP_F->{
                                    binding.tvTemp.text=WeatherHelper.fromKtoF(it.data.main.temp!!).toInt().toString()
                                    binding.tvHomeTempDisc.text="F"
                                }
                                else->{
                                    binding.tvTemp.text=it.data.main.temp?.toInt().toString()
                                    binding.tvHomeTempDisc.text="K"
                                }
                            }

                        }else{
                            binding.tvTemp.text="0"
                        }

                        if(it.data.weather.get(0).description!=null){
                            binding.tvHomeWeatherDescription.text=it.data.weather.get(0).description
                        }else{
                            binding.tvHomeWeatherDescription.text="null"
                        }
                        if(it.data.main.pressure!=null){
                            binding.tvPressure.text=it.data.main.pressure.toString()
                        }else{
                            binding.tvPressure.text="0"
                        }
                        if(it.data.main.humidity!=null){
                            binding.tvHumidity.text=it.data.main.humidity.toString()
                        }else{
                            binding.tvHumidity.text="0"
                        }
                        if(it.data.wind.speed!=null){
                            binding.tvWindSpeed.text=it.data.wind.speed.toString()
                        }else{
                            binding.tvWindSpeed.text="0"
                        }
                        if(it.data.clouds.all!=null){
                            binding.tvClouds.text=it.data.clouds.all.toString()
                        }else{
                            binding.tvClouds.text="0"
                        }
                        if(it.data.rain.`1h`!=null){
                            binding.tvUVI.text=it.data.rain.`1h`.toString()
                        }else{
                            binding.tvUVI.text="0"
                        }
                    }
                   is ApiState.Loading->{
                       binding.parentView.visibility=View.GONE
                       binding.HomeprogressBar.visibility=View.VISIBLE

                        }
                   else ->{
                       Toast.makeText(requireContext(),"Data Failed",Toast.LENGTH_LONG).show()
                   }
                }
            }
        }
        viewModel.getForecast(lat,lon,Consts.API_KEY)
        lifecycleScope.launch{
            viewModel.forecast.collect{
                when(it){
                   is ForcastState.Success->{
                       binding.HomeprogressBar.visibility=View.GONE
                       binding.parentView.visibility=View.VISIBLE
                       forcastResponse=it.data
                       val listD=WeatherHelper.getDaily(it.data,"en")
                       val listH=WeatherHelper.getHourly(it.data)
                       dailyAdapter= DailyAdapter(requireContext())
                       dailyAdapter.submitList(listD)
                       binding.recyclerViewDaily.apply {
                           adapter=dailyAdapter
                           layoutManager= LinearLayoutManager(context).apply {
                               orientation= RecyclerView.VERTICAL
                           }


                       }
                       hourlyAdapter= HourlyAdapter(requireContext())
                       hourlyAdapter.submitList(listH)
                       binding.recyclerViewHourly.apply {
                           adapter=hourlyAdapter
                           layoutManager= LinearLayoutManager(context).apply {
                               orientation= RecyclerView.HORIZONTAL
                           }
                       }
                       saveHome(weatherResponse, forcastResponse)
                   }
                   is ForcastState.Loading->{
                       binding.parentView.visibility=View.GONE
                       binding.HomeprogressBar.visibility=View.VISIBLE
                   }
                   else ->{
                       Toast.makeText(requireContext(),"Data Failed",Toast.LENGTH_LONG).show()
                   }

                }


        }
    }


}
    fun cameFromMap(weatherResponse: WeatherResponse){
        binding.tvCity.text=weatherResponse.name
        WeatherHelper.setDescr(weatherResponse.weather.get(0).description)
        when(sharedPrefViewModel.getTemp()){
            Consts.TEMP_F->{binding.tvTemp.text=WeatherHelper.fromCtoF(weatherResponse.main.temp).toString()
                binding.tvHomeTempDisc.text="F"
            }
            Consts.TEMP_K->{binding.tvTemp.text=WeatherHelper.fromCtoK(weatherResponse.main.temp).toString()
                binding.tvHomeTempDisc.text="K"
            }
            else->{
                binding.tvTemp.text=weatherResponse.main.temp.toString()
                binding.tvHomeTempDisc.text="C"
            }
        }
        when(sharedPrefViewModel.getWindSpeed()){
            Consts.WIND_MS->{binding.tvWindSpeed.text=weatherResponse.wind.speed.toString()}
            else->{
                binding.tvWindSpeed.text=WeatherHelper.fromMStoMH(weatherResponse.wind.speed).toString()
            }
        }
        binding.tvHomeWeatherDescription.text=weatherResponse.weather.get(0).description
        binding.tvPressure.text=weatherResponse.main.pressure.toString()
        binding.tvHumidity.text=weatherResponse.main.humidity.toString()
        binding.tvClouds.text=weatherResponse.clouds.all.toString()
        binding.tvUVI.text="0"
        viewModel.getForecast(weatherResponse.coord.lat,weatherResponse.coord.lon,Consts.API_KEY)
        lifecycleScope.launch{
            viewModel.forecast.collect{
                when(it){
                    is ForcastState.Success->{
                        binding.HomeprogressBar.visibility=View.GONE
                        binding.parentView.visibility=View.VISIBLE
                        val listD=WeatherHelper.getDaily(it.data,"en")
                        val listH=WeatherHelper.getHourly(it.data)
                        dailyAdapter= DailyAdapter(requireContext())
                        dailyAdapter.submitList(listD)
                        binding.recyclerViewDaily.apply {
                            adapter=dailyAdapter
                            layoutManager= LinearLayoutManager(context).apply {
                                orientation= RecyclerView.VERTICAL
                            }


                        }
                        hourlyAdapter= HourlyAdapter(requireContext())
                        hourlyAdapter.submitList(listH)
                        binding.recyclerViewHourly.apply {
                            adapter=hourlyAdapter
                            layoutManager= LinearLayoutManager(context).apply {
                                orientation= RecyclerView.HORIZONTAL
                            }
                        }
                    }
                    is ForcastState.Loading->{
                        binding.parentView.visibility=View.GONE
                        binding.HomeprogressBar.visibility=View.VISIBLE
                    }
                    else ->{
                        Toast.makeText(requireContext(),"Data Failed",Toast.LENGTH_LONG).show()
                    }
                }


            }
        }


    }
    fun saveHome(weatherResponse: WeatherResponse,forcastResponse: ForcastResponse){

        if(sharedPrefViewModel.getLang()==Consts.LANG_EN){
            val listD=WeatherHelper.getDaily(forcastResponse,"en")
            val listH=WeatherHelper.getHourly(forcastResponse)
            val stringD=WeatherHelper.compainObject(listD)
            val stringH=WeatherHelper.compainObject(listH)
            val fake=HomeFake(weatherResponse.id,weatherResponse.main.temp,weatherResponse.name,weatherResponse.main.pressure,
                weatherResponse.main.humidity,weatherResponse.visibility,weatherResponse.clouds.all,weatherResponse.weather.get(0).icon,
            stringD,stringH,weatherResponse.wind.speed)
            lifecycleScope.launch {
                homeViewModel.addFavTODB(fake)
            }

        }else{
            val listD=WeatherHelper.getDaily(forcastResponse,"ar")
            val listH=WeatherHelper.getHourly(forcastResponse)
            val stringD=WeatherHelper.compainObject(listD)
            val stringH=WeatherHelper.compainObject(listH)
            val fake=HomeFake(weatherResponse.id,weatherResponse.main.temp,weatherResponse.name,weatherResponse.main.pressure,
                weatherResponse.main.humidity,weatherResponse.visibility,weatherResponse.clouds.all,weatherResponse.weather.get(0).icon,
                stringD,stringH,weatherResponse.wind.speed)
            lifecycleScope.launch {
                homeViewModel.addFavTODB(fake)
            }
        }



    }


}
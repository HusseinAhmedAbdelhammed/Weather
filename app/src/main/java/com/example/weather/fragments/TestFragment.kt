package com.example.weather.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.weather.WeatherResponse
import com.example.weather.R
import com.example.weather.adapters.DailyAdapter
import com.example.weather.adapters.HourlyAdapter
import com.example.weather.databinding.FragmentTestBinding
import com.example.weather.helpers.Consts
import com.example.weather.helpers.LocationByGPS
import com.example.weather.helpers.Navigator
import com.example.weather.helpers.WeatherHelper
import com.example.weather.viewmodels.SharedPrefViewModel
import com.example.weather.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestFragment : Fragment(),GPSFragmentInterface{

    private val locationPermissionCode = 2
    lateinit var binding:FragmentTestBinding
    private val viewModel: WeatherViewModel by viewModels()
    private val sharedPrefViewModel:SharedPrefViewModel by viewModels()
    lateinit var dailyAdapter: DailyAdapter
    lateinit var hourlyAdapter: HourlyAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    if(it!=null){
                        cameFromMap(it)
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
            }
            else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun setLatAndLon(lat: Double, lon: Double) {
        viewModel.getWeather(lat,lon,"en",Consts.API_KEY)

        lifecycleScope.launch{
            viewModel.weather.collect{
                if(it?.name!=null){
                    binding.tvCity.text=it?.name
                } else{
                    binding.tvCity.text="null city"
                }
                if(it?.main?.temp!=null){
                    when(sharedPrefViewModel.getTemp()){
                        Consts.TEMP_C->{binding.tvTemp.text=WeatherHelper.fromKtoC(it?.main?.temp!!).toInt().toString()
                            binding.tvHomeTempDisc.text="C"
                        }
                        Consts.TEMP_F->{
                            binding.tvTemp.text=WeatherHelper.fromKtoF(it?.main?.temp!!).toInt().toString()
                            binding.tvHomeTempDisc.text="F"
                        }
                        else->{
                            binding.tvTemp.text=it?.main?.temp?.toInt().toString()
                            binding.tvHomeTempDisc.text="K"
                        }
                    }

                }else{
                    binding.tvTemp.text="0"
                }

                if(it?.weather?.get(0)?.description!=null){
                    binding.tvHomeWeatherDescription.text=it?.weather?.get(0)?.description
                }else{
                    binding.tvHomeWeatherDescription.text="null"
                }
                if(it?.main?.pressure!=null){
                    binding.tvPressure.text=it?.main?.pressure.toString()
                }else{
                    binding.tvPressure.text="0"
                }
                if(it?.main?.humidity!=null){
                    binding.tvHumidity.text=it?.main?.humidity.toString()
                }else{
                    binding.tvHumidity.text="0"
                }
                if(it?.wind?.speed!=null){
                    binding.tvWindSpeed.text=it?.wind?.speed.toString()
                }else{
                    binding.tvWindSpeed.text="0"
                }
                if(it?.clouds?.all!=null){
                    binding.tvClouds.text=it?.clouds?.all.toString()
                }else{
                    binding.tvClouds.text="0"
                }
                if(it?.rain?.`1h`!=null){
                    binding.tvUVI.text=it?.rain?.`1h`.toString()
                }else{
                    binding.tvUVI.text="0"
                }
            }
        }
        viewModel.getForecast(lat,lon,Consts.API_KEY)
        lifecycleScope.launch{
            viewModel.forecast.collect{
                if(it!=null){
                    val listD=WeatherHelper.getDaily(it,"en")
                    val listH=WeatherHelper.getHourly(it)
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


        }
    }

}
    fun cameFromMap(weatherResponse: WeatherResponse){
        binding.tvCity.text=weatherResponse.name
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
        viewModel.getForecast(weatherResponse.coord.lat,weatherResponse.coord.lon,Consts.API_KEY)
        lifecycleScope.launch{
            viewModel.forecast.collect{
                if(it!=null){
                    val listD=WeatherHelper.getDaily(it,"en")
                    val listH=WeatherHelper.getHourly(it)
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


            }
        }


    }}
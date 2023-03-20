package com.example.weather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.adapters.DailyAdapter
import com.example.weather.adapters.HourlyAdapter
import com.example.weather.databinding.FragmentFavDetailsBinding
import com.example.weather.helpers.Consts
import com.example.weather.helpers.WeatherHelper
import com.example.weather.helpers.state.ApiState
import com.example.weather.helpers.state.ForcastState
import com.example.weather.viewmodels.SharedPrefViewModel
import com.example.weather.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class FavDetailsFragment : Fragment() {
    lateinit var binding:FragmentFavDetailsBinding
    private val viewModel: WeatherViewModel by viewModels()
    private val sharedPrefViewModel: SharedPrefViewModel by viewModels()
    lateinit var dailyAdapter: DailyAdapter
    lateinit var hourlyAdapter: HourlyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFavDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(FavDetailsFragmentArgs.fromBundle(requireArguments()).fdlat!=null){
            val lat=FavDetailsFragmentArgs.fromBundle(requireArguments()).fdlat
            val lon=FavDetailsFragmentArgs.fromBundle(requireArguments()).fdlon
            if(sharedPrefViewModel.getLang()==Consts.LANG_EN){
                viewModel.getWeather(lat.toDouble(),lon.toDouble(),"en",Consts.API_KEY)
            }else{
                viewModel.getWeather(lat.toDouble(),lon.toDouble(),"ar",Consts.API_KEY)
            }
                lifecycleScope.launch{
                    viewModel.weather.collect{
                        when(it) {
                            is ApiState.Success->{
                                binding.parentView.visibility=View.VISIBLE
                                binding.HomeFprogressBar.visibility=View.GONE
                                binding.tvFCity.text = it.data.name
                                when (sharedPrefViewModel.getTemp()) {
                                    Consts.TEMP_F -> {
                                        binding.tvFTemp.text =
                                            WeatherHelper.fromCtoF(it.data.main.temp).toString()
                                        //binding.tvFHomeTempDisc.text="F"
                                    }
                                    Consts.TEMP_K -> {
                                        binding.tvFTemp.text =
                                            WeatherHelper.fromCtoK(it.data.main.temp).toString()
                                        //binding.tvFHomeTempDisc.text="K"
                                    }
                                    else -> {
                                        binding.tvFTemp.text = it.data.main.temp.toString()
                                        binding.tvFHomeTempDisc.text = "C"
                                    }
                                }
                                when (sharedPrefViewModel.getWindSpeed()) {
                                    Consts.WIND_MS -> {
                                        binding.tvFWindSpeed.text = it.data.wind.speed.toString()
                                    }
                                    else -> {
                                        binding.tvFWindSpeed.text =
                                            WeatherHelper.fromMStoMH(it.data.wind.speed).toString()
                                    }
                                }
                                binding.tvFPressure.text = it.data.main.pressure.toString()
                                binding.tvFHomeWeatherDescription.text =
                                    it.data.weather.get(0).description
                                binding.tvFHumidity.text = it.data.main.humidity.toString()
                                binding.tvFClouds.text = it.data.clouds.all.toString()
                                binding.tvFUVI.text = it.data.rain.`1h`.toString()
                            }
                            is ApiState.Loading->{
                                binding.parentView.visibility=View.GONE
                                binding.HomeFprogressBar.visibility=View.VISIBLE
                            }
                            else ->{
                                Toast.makeText(requireContext(),"Data Failed", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
                viewModel.getForecast(lat.toDouble(),lon.toDouble(),Consts.API_KEY)
                lifecycleScope.launch {
                    viewModel.forecast.collect{
                        when(it){
                            is ForcastState.Success->{
                                binding.parentView.visibility=View.VISIBLE
                                binding.HomeFprogressBar.visibility=View.GONE
                                val listD=WeatherHelper.getDaily(it.data,"en")
                                val listH=WeatherHelper.getHourly(it.data)
                                dailyAdapter= DailyAdapter(requireContext())
                                dailyAdapter.submitList(listD)
                                binding.recyclerFViewDaily.apply {
                                    adapter=dailyAdapter
                                    layoutManager= LinearLayoutManager(context).apply {
                                        orientation= RecyclerView.VERTICAL
                                    }


                                }
                                hourlyAdapter= HourlyAdapter(requireContext())
                                hourlyAdapter.submitList(listH)
                                binding.recyclerFViewHourly.apply {
                                    adapter=hourlyAdapter
                                    layoutManager= LinearLayoutManager(context).apply {
                                        orientation= RecyclerView.HORIZONTAL
                                    }
                                }
                            }
                            is ForcastState.Loading->{
                                binding.parentView.visibility=View.GONE
                                binding.HomeFprogressBar.visibility=View.VISIBLE
                            }
                            else ->{
                                Toast.makeText(requireContext(),"Data Failed", Toast.LENGTH_LONG).show()
                            }

                        }
                    }
                }


        }
    }
}
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
                        if(it!=null) {
                            binding.tvFCity.text = it?.name
                            when (sharedPrefViewModel.getTemp()) {
                                Consts.TEMP_F -> {
                                    binding.tvFTemp.text =
                                        WeatherHelper.fromCtoF(it?.main?.temp!!).toString()
                                    //binding.tvFHomeTempDisc.text="F"
                                }
                                Consts.TEMP_K -> {
                                    binding.tvFTemp.text =
                                        WeatherHelper.fromCtoK(it?.main?.temp!!).toString()
                                    //binding.tvFHomeTempDisc.text="K"
                                }
                                else -> {
                                    binding.tvFTemp.text = it?.main?.temp.toString()
                                    binding.tvFHomeTempDisc.text = "C"
                                }
                            }
                            when (sharedPrefViewModel.getWindSpeed()) {
                                Consts.WIND_MS -> {
                                    binding.tvFWindSpeed.text = it?.wind?.speed.toString()
                                }
                                else -> {
                                    binding.tvFWindSpeed.text =
                                        WeatherHelper.fromMStoMH(it?.wind?.speed!!).toString()
                                }
                            }
                            binding.tvFPressure.text = it?.main?.pressure.toString()
                            binding.tvFHomeWeatherDescription.text =
                                it?.weather?.get(0)?.description
                            binding.tvFHumidity.text = it?.main?.humidity.toString()
                            binding.tvFClouds.text = it?.clouds?.all.toString()
                            binding.tvFUVI.text = it?.rain?.`1h`.toString()
                        }
                    }
                }
                viewModel.getForecast(lat.toDouble(),lon.toDouble(),Consts.API_KEY)
                lifecycleScope.launch {
                    viewModel.forecast.collect{
                        if(it!=null){

                            val listD=WeatherHelper.getDaily(it,"en")
                            val listH=WeatherHelper.getHourly(it)
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
                    }
                }


        }
    }
}
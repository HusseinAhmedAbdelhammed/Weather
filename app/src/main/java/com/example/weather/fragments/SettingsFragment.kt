package com.example.weather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.weather.R
import com.example.weather.databinding.FragmentFavBinding
import com.example.weather.databinding.FragmentSettingsBinding
import com.example.weather.helpers.Consts
import com.example.weather.helpers.Navigator
import com.example.weather.viewmodels.SharedPrefViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding
    private val viewModel:SharedPrefViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Navigator.hasEntered=""
        if(viewModel.getTemp() == ""||viewModel.getTemp()==Consts.TEMP_C){
            binding.Celsius.isChecked=true
        }
        if(viewModel.getTemp() == Consts.TEMP_F){
            binding.Kelvin.isChecked=true
        }
        if(viewModel.getTemp()==Consts.TEMP_F){
            binding.Fahrenheit.isChecked=true
        }
        if(viewModel.getLang()==""||viewModel.getLang()==Consts.LANG_EN){
            binding.english.isChecked=true
        }
        if(viewModel.getLang()==Consts.LANG_AR){
            binding.arabic.isChecked=true
        }
        if(viewModel.getWindSpeed()==""||viewModel.getWindSpeed()==Consts.WIND_MS){
            binding.MeterSec.isChecked=true
        }
        if(viewModel.getWindSpeed()==Consts.WIND_MH){
            binding.MileHour.isChecked=true
        }
        if(viewModel.getLocation()==""||viewModel.getLocation()==Consts.LOCATION_GPS){
            binding.GPSRbtn.isChecked=true
        }
        if(viewModel.getLocation()==Consts.LOCATION_MAP){
            binding.mapRBtn.isChecked=true
        }
        if(viewModel.getNotification()==""||viewModel.getNotification()==Consts.NOT_OFF){
            binding.off.isChecked=true
        }
        if(viewModel.getNotification()==Consts.NOT_ON){
            binding.on.isChecked=true
        }
        if(viewModel.getNotificationType()==""||viewModel.getNotificationType()==Consts.NOT_TYPE_NOTI){
            binding.notif.isChecked=true
        }
        if(viewModel.getNotificationType()==Consts.NOT_TYPE_ALARM){
            binding.alert.isChecked=true
        }
        binding.Celsius.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.tempSetting(Consts.TEMP_C)
            }

        })
        binding.Kelvin.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.tempSetting(Consts.TEMP_K)
            }
        })
        binding.Fahrenheit.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.tempSetting(Consts.TEMP_F)
            }
        })
        binding.MeterSec.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.windSpeedSetting(Consts.WIND_MS)
            }
        })
        binding.MileHour.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.windSpeedSetting(Consts.WIND_MH)
            }
        })
        binding.english.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.languageSettings(Consts.LANG_EN)
            }
        })
        binding.arabic.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.languageSettings(Consts.LANG_AR)
            }
        })
        binding.on.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.notificationSettings(Consts.NOT_ON)
            }
        })
        binding.off.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.notificationSettings(Consts.NOT_OFF)
            }
        })
        binding.notif.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.notificationTypeSettings(Consts.NOT_TYPE_NOTI)
            }
        })
        binding.alert.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.notificationTypeSettings(Consts.NOT_TYPE_ALARM)
            }
        })
        binding.GPSRbtn.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.locationSettings(Consts.LOCATION_GPS)
            }
        })
        binding.mapRBtn.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.locationSettings(Consts.LOCATION_MAP)
            }
        })
    }
}
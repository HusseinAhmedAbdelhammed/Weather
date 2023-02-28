package com.example.weather.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.forcast.ForcastResponse
import com.example.domain.entity.weather.Weather
import com.example.domain.entity.weather.WeatherResponse
import com.example.domain.usecase.GetForcast
import com.example.domain.usecase.GetWeather
import com.example.weather.helpers.state.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor (private val getWeatherUseCase:GetWeather,private val getForcast: GetForcast):ViewModel() {
    private val _weather:MutableStateFlow<WeatherResponse?> = MutableStateFlow(null)
    val weather:StateFlow<WeatherResponse?> = _weather
    private val _weatherState:MutableStateFlow<ApiState?> = MutableStateFlow(null)
    val weatherState:StateFlow<ApiState?> = _weatherState
    fun getWeather(lat:Double,lon:Double,lang:String,apiKey:String){
        viewModelScope.launch {
            try {
                _weatherState.value=ApiState.Loading
                _weather.value = getWeatherUseCase(lat,lon,lang,apiKey)
                _weatherState.value=ApiState.Success(getWeatherUseCase(lat,lon,lang,apiKey))
            }catch (e:Exception){
                //Log.i("TAG", "getWeather: ${e.message}")
                _weatherState.value=ApiState.Failure(e)
            }
        }
    }
    private val _forecast:MutableStateFlow<ForcastResponse?> = MutableStateFlow(null)
    val forecast:StateFlow<ForcastResponse?> = _forecast
    fun getForecast(lat: Double,lon: Double,apiKey: String){
        viewModelScope.launch {
            try {
                _forecast.value = getForcast(lat,lon,apiKey)
            }catch (e:Exception){

            }
        }
    }
}
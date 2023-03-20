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
import com.example.weather.helpers.state.ForcastState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor (private val getWeatherUseCase:GetWeather,private val getForcast: GetForcast):ViewModel() {
    private val _weather:MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Loading)
    val weather:StateFlow<ApiState> = _weather
    fun getWeather(lat:Double,lon:Double,lang:String,apiKey:String){
        viewModelScope.launch {
            try {

                _weather.value = ApiState.Success(getWeatherUseCase(lat,lon,lang,apiKey))
            }catch (e:Exception){
                //Log.i("TAG", "getWeather: ${e.message}")
                _weather.value=ApiState.Failure(e)
            }
        }
    }
    private val _forecast:MutableStateFlow<ForcastState> = MutableStateFlow(ForcastState.Loading)
    val forecast:StateFlow<ForcastState> = _forecast
    fun getForecast(lat: Double,lon: Double,apiKey: String){
        viewModelScope.launch {
            try {
                _forecast.value = ForcastState.Success(getForcast(lat,lon,apiKey))
            }catch (e:Exception){
                _forecast.value=ForcastState.Failure(e)
            }
        }
    }
}
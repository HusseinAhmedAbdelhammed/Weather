package com.example.weather.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.fakeentity.AlertDomainEntity
import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.usecase.*
import com.example.weather.helpers.state.AlertState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertViewModel@Inject constructor(private val getAlerts: GetAlerts, private val addAlert: AddAlert, private val delalert: DelAlert):
    ViewModel() {
    private val _alertList: MutableStateFlow<AlertState> = MutableStateFlow(AlertState.Loading)
    val alertList: StateFlow<AlertState> = _alertList
    fun getAlertList(){
        viewModelScope.launch(Dispatchers.IO){
            try {
               getAlerts().collect{ _alertList.value=AlertState.Success(it)}
            }catch (e:Exception){
                AlertState.Failure(e)
            }
        }
    }
    fun addAlertTODB(alert: AlertDomainEntity){
        viewModelScope.launch(Dispatchers.IO){
            addAlert(alert)
        }
    }
    fun delAlertFromDB(alert: AlertDomainEntity){
        viewModelScope.launch(Dispatchers.IO){
            delalert(alert)
        }
    }

}
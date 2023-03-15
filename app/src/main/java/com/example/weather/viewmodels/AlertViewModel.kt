package com.example.weather.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.fakeentity.AlertDomainEntity
import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertViewModel@Inject constructor(private val getAlerts: GetAlerts, private val addAlert: AddAlert, private val delalert: DelAlert):
    ViewModel() {
    private val _alertList: MutableStateFlow<List<AlertDomainEntity>?> = MutableStateFlow(null)
    val alertList: StateFlow<List<AlertDomainEntity>?> = _alertList
    fun getAlertList(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                _alertList.value=getAlerts()
            }catch (e:Exception){
                Log.i("SONIC", "getFavList: ${e.message}")
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
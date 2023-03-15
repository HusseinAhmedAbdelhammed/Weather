package com.example.weather.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.entity.fakeentity.HomeFake
import com.example.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(private val getHome: GetHome, private val addHome: AddHome):
    ViewModel() {
    private val _homeList: MutableStateFlow<List<HomeFake>?> = MutableStateFlow(null)
    val homeList: StateFlow<List<HomeFake>?> = _homeList
    fun getHomeList(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                _homeList.value=getHome()
            }catch (e:Exception){
                Log.i("SONIC", "getFavList: ${e.message}")
            }
        }
    }
    fun addFavTODB(home: HomeFake){
        viewModelScope.launch(Dispatchers.IO){
            addHome(home)
        }
    }

    }


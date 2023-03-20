package com.example.weather.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.usecase.AddFav
import com.example.domain.usecase.DelFav
import com.example.domain.usecase.GetFav
import com.example.weather.helpers.state.FavState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(private val getFav: GetFav,private val addFav: AddFav, private val delFav: DelFav):ViewModel() {
    private val _favList:MutableStateFlow<FavState> = MutableStateFlow(FavState.Loading)
    val favList:StateFlow<FavState> = _favList
    fun getFavList(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                getFav().collect{_favList.value=FavState.Success(it)}
            }catch (e:Exception){
                _favList.value=FavState.Failure(e)
            }
        }
    }
    fun addFavTODB(fav:FavDomainEntity){
        viewModelScope.launch(Dispatchers.IO){
            addFav(fav)
        }
    }
    fun delFavFromDB(fav:FavDomainEntity){
        viewModelScope.launch(Dispatchers.IO){
            delFav(fav)
        }
    }


}
package com.example.weather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.domain.usecase.AddFav
import com.example.domain.usecase.GetFav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(private val getFav: GetFav,private val addFav: AddFav, private val delFav: AddFav):ViewModel() {
    private val _favList:MutableStateFlow<List<FavDomainEntity>?> = MutableStateFlow(null)
    val favList:StateFlow<List<FavDomainEntity>?> = _favList
    fun getFavList(){
        viewModelScope.launch {
            try {
                _favList.value=getFav()
            }catch (e:Exception){

            }
        }
    }
    fun addFavTODB(fav:FavDomainEntity){
        viewModelScope.launch{
            addFav(fav)
        }
    }
    fun delFavFromDB(fav:FavDomainEntity){
        viewModelScope.launch {
            delFav(fav)
        }
    }


}
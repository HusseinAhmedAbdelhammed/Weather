package com.example.domain.entity.forcast

data class ForcastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    //use serializable to change it to forcast
    val list: List<Forcast>,
    val message: Int
)
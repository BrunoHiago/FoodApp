package com.example.foodapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    var id:String,
    var name:String,
    var description:String,
    var price:Double,
    var photo:String,
    var category:String,
    var restaurantId:String,
)
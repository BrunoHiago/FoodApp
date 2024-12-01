package com.example.foodapp.data.model

import android.graphics.Bitmap

data class Product(
    var id:String,
    var name:String,
    var description:String,
    var price:Double,
    var photo:Bitmap,
    var category:String,
    var restaurantId:String,
)


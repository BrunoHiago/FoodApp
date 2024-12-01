package com.example.foodapp.data.model

import android.graphics.Bitmap

data class Restaurant (
    var id:String,
    var name:String,
    var address:String,
    var cep:String,
    var rating:Double,
    var photo:Bitmap,
    var description:String,
    var phone:String,
    var category:String,
    var favorite:Boolean
)


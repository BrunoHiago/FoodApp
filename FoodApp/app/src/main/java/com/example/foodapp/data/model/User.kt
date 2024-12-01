package com.example.foodapp.data.model

import android.graphics.Bitmap


data class User(
    val id: String,
    val name: String,
    val email: String,
    val photo: Bitmap
)
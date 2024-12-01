package com.example.foodapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteDto (
    val userId: String,
    val restaurantId: String
)
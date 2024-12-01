package com.example.foodapp.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantDto(
    val id: String,
    val name: String,
    val address: String,
    val rating: Double,
    val photo: String,
    val description: String,
    val category: String,
    val phone: String,
    val cep: String,
    val favorite: Boolean
)
package com.example.foodapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    var id: String,
    var name: String,
    var email: String,
    var photo: String = "",
)

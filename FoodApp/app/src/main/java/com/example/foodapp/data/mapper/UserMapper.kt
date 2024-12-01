package com.example.foodapp.data.mapper

import com.example.foodapp.data.dto.UserDto
import com.example.foodapp.data.model.User
import com.example.foodapp.utils.ImageUtils

object UserMapper {

    fun toDto(user: User): UserDto {
        return UserDto(
            id = user.id,
            name = user.name,
            email = user.email,
            photo = ImageUtils.bitmapToBase64(user.photo),
        )
    }

    fun fromDto(userDto: UserDto): User {
        return User(
            id = userDto.id,
            name = userDto.name,
            email = userDto.email,
            photo = ImageUtils.base64ToBitmap(userDto.photo),
        )
    }
}
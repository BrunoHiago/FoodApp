package com.example.foodapp.data.mapper

import com.example.foodapp.data.dto.RestaurantDto
import com.example.foodapp.data.model.Restaurant
import com.example.foodapp.utils.ImageUtils

object RestaurantMapper {
    fun toDto(restaurant: Restaurant): RestaurantDto {
        return RestaurantDto(
            id = restaurant.id,
            name = restaurant.name,
            address = restaurant.address,
            cep = restaurant.cep,
            rating = restaurant.rating,
            photo = ImageUtils.bitmapToBase64(restaurant.photo),
            description = restaurant.description,
            phone = restaurant.phone,
            category = restaurant.category,
            favorite = restaurant.favorite
        )
    }

    fun fromDto(restaurantDTO: RestaurantDto): Restaurant {
        return Restaurant(
            id = restaurantDTO.id,
            name = restaurantDTO.name,
            address = restaurantDTO.address,
            cep = restaurantDTO.cep,
            rating = restaurantDTO.rating,
            photo = ImageUtils.base64ToBitmap(restaurantDTO.photo),
            description = restaurantDTO.description,
            phone = restaurantDTO.phone,
            category = restaurantDTO.category,
            favorite = restaurantDTO.favorite
        )
    }

}
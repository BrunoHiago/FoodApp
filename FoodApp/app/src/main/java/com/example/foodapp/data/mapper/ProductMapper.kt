package com.example.foodapp.data.mapper

import com.example.foodapp.data.dto.ProductDto
import com.example.foodapp.data.model.Product
import com.example.foodapp.utils.ImageUtils

object ProductMapper {
    fun toDto(product: Product): ProductDto {
        return ProductDto(
            id = product.id,
            name = product.name,
            description = product.description,
            price = product.price,
            photo = ImageUtils.bitmapToBase64(product.photo),
            category = product.category,
            restaurantId = product.restaurantId
        )
    }

    fun fromDto(productDTO: ProductDto): Product {
        return Product(
            id = productDTO.id,
            name = productDTO.name,
            description = productDTO.description,
            price = productDTO.price,
            photo = ImageUtils.base64ToBitmap(productDTO.photo),
            category = productDTO.category,
            restaurantId = productDTO.restaurantId
        )
    }

}
package com.example.foodapp.data.repository

import android.content.Context
import android.util.Log
import com.example.foodapp.data.dto.FavoriteDto
import com.example.foodapp.data.dto.RestaurantDto
import com.example.foodapp.data.mapper.RestaurantMapper
import com.example.foodapp.data.model.Restaurant
import com.example.foodapp.service.ApiClient
import com.example.foodapp.utils.TokenManager
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.first

class RestaurantRepository(private val context: Context) {
    private val client = ApiClient.client

    suspend fun addRestaurant(restaurant: Restaurant) {
        val restaurantDto = RestaurantMapper.toDto(restaurant)


        client.post("restaurants") {
            contentType(ContentType.Application.Json)
            setBody(restaurantDto)
        }
    }

    suspend fun getAllRestaurants(): List<Restaurant> {
        Log.d("RestaurantRepository", "Buscando restaurantes...")

        try {
            val token = TokenManager.getToken(context).first()
            val userId = TokenManager.getUserId(context).first()

            val response = client.get("restaurant/${userId}") {
                header("Authorization", "Bearer $token")
            }

            return if (response.status.value == 200) {
                val restaurantsDto: List<RestaurantDto> = response.body()
                restaurantsDto.map { restaurantDto ->
                    RestaurantMapper.fromDto(restaurantDto)
                }
            } else if (response.status.value == 401) {
                Log.d("RestaurantRepository", "Token inválido")
                TokenManager.clearToken(context)
                emptyList()
            } else {
                emptyList()
            }

        } catch (e: Exception) {
            Log.e("RestaurantRepository", "Erro ao buscar restaurantes: ${e.message}")
            return emptyList()
        }
    }


    suspend fun updateRestaurant(id: String, restaurant: Restaurant) {
        val restaurantDto = RestaurantMapper.toDto(restaurant)
        client.put("restaurants/$id") {
            contentType(ContentType.Application.Json)
            setBody(restaurantDto)
        }
    }

    suspend fun deleteRestaurant(id: String) {
        client.delete("restaurants/$id")
    }

    suspend fun getFavoriteRestaurants(userId: String): List<Restaurant> {
        Log.d("RestaurantRepository", "Buscando restaurantes...")

        try {
            val token = TokenManager.getToken(context).first()

            val response = client.get("favorite/user/${userId}") {
                header("Authorization", "Bearer $token")
            }

            return if (response.status.value == 200) {
                val restaurantsDto: List<RestaurantDto> = response.body()
                restaurantsDto.map { restaurantDto ->
                    RestaurantMapper.fromDto(restaurantDto)
                }
            } else if (response.status.value == 401) {
                Log.d("RestaurantRepository", "Token inválido")
                TokenManager.clearToken(context)
                emptyList()
            } else {
                emptyList()
            }

        } catch (e: Exception) {
            Log.e("RestaurantRepository", "Erro ao buscar restaurantes: ${e.message}")
            return emptyList()
        }

    }

    suspend fun addFavoriteRestaurant(favoriteDto: FavoriteDto) {
        return try {
            val token = TokenManager.getToken(context).first()

            client.post("favorite") {
                contentType(ContentType.Application.Json)
                setBody(favoriteDto)
                header("Authorization", "Bearer $token")
            }.body()
        } catch (e: Exception) {
            Log.e("RestaurantRepository", "Error adding favorite: ${e.message}")
            throw e
        }
    }

    suspend fun deleteFavoriteRestaurant(favoriteDto: FavoriteDto) {
        return try {
            val token = TokenManager.getToken(context).first()

            client.delete("favorite/${favoriteDto.userId}/${favoriteDto.restaurantId}") {
                header("Authorization", "Bearer $token")
            }.body()
        } catch (e: Exception) {
            Log.e("RestaurantRepository", "Error deleting favorite: ${e.message}")
            throw e
        }

    }

}

package com.example.foodapp.data.repository

import android.content.Context
import android.util.Log
import com.example.foodapp.data.dto.ProductDto
import com.example.foodapp.data.dto.RestaurantDto
import com.example.foodapp.data.mapper.ProductMapper
import com.example.foodapp.data.mapper.RestaurantMapper
import com.example.foodapp.data.model.Product
import com.example.foodapp.service.ApiClient
import com.example.foodapp.utils.TokenManager
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import kotlinx.coroutines.flow.first

class ProductRepository(private val context: Context) {
    private val client = ApiClient.client

    suspend fun getAllProducts(): List<Product>{
        Log.d("ProductRepository", "Buscando produtos...")

        try {
            val token = TokenManager.getToken(context).first()

            val response = client.get("product") {
                header("Authorization", "Bearer $token")
            }

            return if (response.status.value == 200) {
                val productsDto: List<ProductDto> = response.body()
                productsDto.map { productDto ->
                    ProductMapper.fromDto(productDto)
                }
            } else if (response.status.value == 401) {
                Log.d("ProductRepository", "Token inválido")
                TokenManager.clearToken(context)
                emptyList()
            } else {
                emptyList()
            }

        } catch (e: Exception) {
            Log.e("ProductRepository", "Erro ao buscar produtos: ${e.message}")
            return emptyList()
        }

    }
}
package com.example.foodapp.data.repository

import android.content.Context
import android.util.Log
import com.example.foodapp.data.dto.LoginResponse
import com.example.foodapp.data.dto.SignInDto
import com.example.foodapp.data.dto.UserDto
import com.example.foodapp.data.mapper.UserMapper
import com.example.foodapp.data.model.User
import com.example.foodapp.service.ApiClient
import com.example.foodapp.utils.TokenManager
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.InternalAPI
import kotlinx.coroutines.flow.first

class UserRepository(private val context: Context) {
    private val client = ApiClient.client

    @OptIn(InternalAPI::class)
    suspend fun login(signInDto: SignInDto): LoginResponse {
        return try {
            client.post("auth/login") {
                contentType(ContentType.Application.Json)
                setBody(signInDto)
            }.body()
        } catch (e: Exception) {
            Log.e("UserRepository", "Error during login: ${e.message}")
            throw e
        }
    }

    suspend fun getUser(userId: String): User {
        return try {
            val token = TokenManager.getToken(context).first()
            val response = client.get("users/$userId") {
                header("Authorization", "Bearer $token")
            }

            if (response.status.value == 200) {
                val userDto = response.body<UserDto>()
                UserMapper.fromDto(userDto)
            } else {
                throw Exception("User not found")
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error during login: ${e.message}")
            throw e
        }
    }
}
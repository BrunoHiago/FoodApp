package com.example.foodapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.dto.SignInDto
import com.example.foodapp.data.mapper.UserMapper
import com.example.foodapp.data.model.User
import com.example.foodapp.data.repository.UserRepository
import com.example.foodapp.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserViewModel(private val context: Context) : ViewModel() {
    private val userRepository = UserRepository(context)
    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage: StateFlow<String> = _errorMessage


    fun login(context: Context, email: String, password: String) {
        _errorMessage.value = ""

        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Preencha todos os campos"
            return
        }
        viewModelScope.launch {
            try {
                val signInDto = SignInDto(email, password)
                val loginResponse = userRepository.login(signInDto)

                TokenManager.saveToken(context, loginResponse.accessToken, loginResponse.user.id)
                Log.d("UserViewModel", "Token salvo: ${loginResponse.accessToken}")
                Log.d("UserViewModel", "User salvo: ${loginResponse.user}")
                _userState.value = UserMapper.fromDto(loginResponse.user)


            } catch (e: Exception) {
                _errorMessage.value = "Email ou senha incorretos"
                e.printStackTrace()
            }
        }
    }

    fun logout(context: Context) {
        viewModelScope.launch {
            TokenManager.clearToken(context)
            _userState.value = null
        }
    }

    fun getUser() {
        viewModelScope.launch {
            try {
                val userId = TokenManager.getUserId(context).first()
                val user = userId?.let { userRepository.getUser(it) }
                _userState.value = user
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }


}
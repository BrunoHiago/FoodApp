package com.example.foodapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.dto.FavoriteDto
import com.example.foodapp.data.model.Restaurant
import com.example.foodapp.data.repository.RestaurantRepository
import com.example.foodapp.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RestaurantViewModel(private val context: Context, private val userViewModel: UserViewModel) :
    ViewModel() {
    private val restaurantRepository = RestaurantRepository(context)

    private val _restaurantsState = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurantsState: StateFlow<List<Restaurant>> = _restaurantsState

    private val _selectedRestaurant = MutableStateFlow<Restaurant?>(null)
    val selectedRestaurant: StateFlow<Restaurant?> = _selectedRestaurant

    private val _favoriteRestaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val favoriteRestaurants: StateFlow<List<Restaurant>> = _favoriteRestaurants

    private val _searchRestaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val searchRestaurants: StateFlow<List<Restaurant>> = _searchRestaurants

    private val _selectedCategory = MutableStateFlow("")
    val selectedCategory: StateFlow<String> = _selectedCategory

    init {
        getAllRestaurants()

    }


    fun setSelectedRestaurant(restaurant: Restaurant?) {
        _selectedRestaurant.value = restaurantsState.value.find { it.id == restaurant?.id }
    }

    fun searchRestaurants(query: String, text: String) {
        val filteredRestaurants = _restaurantsState.value.filter { restaurant ->
            restaurant.name.contains(query, ignoreCase = true) &&
                    if (text == "Todas Lojas") true else restaurant.category.contains(
                        text,
                        ignoreCase = true
                    )
        }
        _searchRestaurants.value = filteredRestaurants
    }

    fun addRestaurant(restaurant: Restaurant) {
        viewModelScope.launch {
            restaurantRepository.addRestaurant(restaurant)
            getAllRestaurants()
        }
    }

    fun getAllRestaurants() {
        viewModelScope.launch {
            val restaurants = restaurantRepository.getAllRestaurants()
            _restaurantsState.value = restaurants
        }
    }

    fun getFavoriteRestaurants() {
        viewModelScope.launch {
            val userId = TokenManager.getUserId(context).first()
            val favoriteRestaurants =
                _restaurantsState.value.filter { restaurant ->
                    restaurant.favorite
                }
            _favoriteRestaurants.value = favoriteRestaurants
        }
    }

    fun updateFavoriteRestaurant(restaurant: Restaurant) {
        viewModelScope.launch {
            val userId = TokenManager.getUserId(context).first()
            Log.d("RestaurantViewModel", "User ID: $userId")
            restaurantRepository.addFavoriteRestaurant(
                FavoriteDto(userId ?: "", restaurant.id)
            )
            getAllRestaurants()
            _searchRestaurants.value = _searchRestaurants.value.map {
                if (it.id == restaurant.id) {
                    it.copy(favorite = !it.favorite)
                } else {
                    it
                }
            }
        }
    }


    fun isFavorite(restaurant: Restaurant): Boolean {
        Log.d(
            "RestaurantViewModel",
            "Checking if $restaurant is a favorite " + _favoriteRestaurants.value.contains(
                restaurant
            )
        )
        return _restaurantsState.value.filter { it.favorite }.contains(restaurant)
    }

    // Função para atualizar restaurante
    fun updateRestaurant(id: String, restaurant: Restaurant) {
        viewModelScope.launch {
            restaurantRepository.updateRestaurant(id, restaurant)
            getAllRestaurants() // Atualiza a lista após atualizar
        }
    }

    // Função para deletar restaurante
    fun deleteRestaurant(id: String) {
        viewModelScope.launch {
            restaurantRepository.deleteRestaurant(id)
            getAllRestaurants() // Atualiza a lista após deletar
        }
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }

    fun getSelectedCategory(): String {
        return _selectedCategory.value.ifEmpty {
            "Todas Lojas"
        }
    }


}
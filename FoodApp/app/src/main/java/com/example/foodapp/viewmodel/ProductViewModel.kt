package com.example.foodapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.model.Product
import com.example.foodapp.data.model.Restaurant
import com.example.foodapp.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel( context: Context) : ViewModel() {
    private val productRepository = ProductRepository(context)

    private val _productsState = MutableStateFlow<List<Product>>(emptyList())
    val productsState: StateFlow<List<Product>> = _productsState

    private val _searchProducts = MutableStateFlow<List<Product>>(emptyList())
    val searchProducts: StateFlow<List<Product>> = _searchProducts

    init {
        getAllProducts()
        searchProducts("", "Todas Lojas", emptyList())
    }

    fun searchProducts(query: String, text: String, restaurants: List<Restaurant>) {
        val filteredProducts = _productsState.value.filter { product ->
            (product.name.contains(query, ignoreCase = true) ||
                    product.description.contains(query, ignoreCase = true) ) &&
                    if (text == "Todas Lojas") true else restaurants.find { it.id == product.restaurantId }?.category?.contains(text, ignoreCase = true) == true
        }
        _searchProducts.value = filteredProducts

    }

    fun searchProductsForRestaurant(restaurantId: String) {
        val filteredProducts = _productsState.value.filter { product ->
            product.restaurantId == restaurantId
        }
        _searchProducts.value = filteredProducts

    }
    fun getAllProducts(){
        viewModelScope.launch {
            val products = productRepository.getAllProducts()
            _productsState.value = products
        }

    }
}
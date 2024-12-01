package com.example.foodapp.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodapp.ui.component.RestaurantItem
import com.example.foodapp.ui.component.SearchBar
import com.example.foodapp.viewmodel.RestaurantViewModel

@Composable
fun FavoriteScreen(restaurantViewModel: RestaurantViewModel, navController: NavHostController) {
    val favoriteRestaurants by restaurantViewModel.restaurantsState.collectAsState()

    var search by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        restaurantViewModel.getFavoriteRestaurants()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        SearchBar(Modifier.padding(bottom = 16.dp)) {
            search = it
        }

        LazyColumn {
            items(favoriteRestaurants){ restaurant ->
                if (restaurant.favorite && restaurant.name.contains(search, ignoreCase = true)){
                    RestaurantItem(
                        restaurant = restaurant,
                        modifier = Modifier.clickable {
                            restaurantViewModel.setSelectedRestaurant(restaurant)
                            navController.navigate("Detail")
                        },
                        onFavoriteClick = {
                            restaurantViewModel.updateFavoriteRestaurant(restaurant)
                        },
                        isFavorite = true
                    )
                }

            }
        }
    }
}
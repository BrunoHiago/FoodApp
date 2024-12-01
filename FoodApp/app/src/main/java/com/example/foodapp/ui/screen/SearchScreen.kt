package com.example.foodapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodapp.ui.component.ProductItem
import com.example.foodapp.ui.component.RestaurantItem
import com.example.foodapp.ui.component.SearchBar
import com.example.foodapp.ui.component.SelectInput
import com.example.foodapp.ui.component.StylishSelect
import com.example.foodapp.utils.Category
import com.example.foodapp.viewmodel.ProductViewModel
import com.example.foodapp.viewmodel.RestaurantViewModel


@Composable
fun SearchScreen(
    restaurantViewModel: RestaurantViewModel,
    productViewModel: ProductViewModel,
    navController: NavHostController
) {

    var selected by remember { mutableStateOf(true) }
    var categorySelected by remember { mutableStateOf(TextFieldValue(restaurantViewModel.getSelectedCategory())) }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        restaurantViewModel.getAllRestaurants()
        productViewModel.getAllProducts()
        restaurantViewModel.searchRestaurants("", categorySelected.text)
        productViewModel.searchProducts(
            "",
            categorySelected.text,
            restaurantViewModel.restaurantsState.value
        )
        restaurantViewModel.getFavoriteRestaurants()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        SearchBar(Modifier.fillMaxWidth()) {
            restaurantViewModel.searchRestaurants(it, categorySelected.text)
            searchText = it
            productViewModel.searchProducts(
                it,
                categorySelected.text,
                restaurantViewModel.restaurantsState.value
            )
        }

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StylishSelect(
                value = categorySelected,
                label = "",
                onOptionSelected = {
                    categorySelected = TextFieldValue(it)
                    restaurantViewModel.searchRestaurants(searchText, categorySelected.text)
                    productViewModel.searchProducts(
                        searchText,
                        categorySelected.text,
                        restaurantViewModel.restaurantsState.value
                    )
                },
                options = listOf("Todas Lojas") + Category.entries.map { it.displayName },
                placeholder = "Selecione uma opção"
            )
            SelectInput(selected = selected) {
                selected = !selected
            }
        }

        if (selected) {
            RestaurantList(restaurantViewModel, navController)
        } else {
            ProductList(productViewModel,restaurantViewModel, navController)
        }


    }
}

@Composable
fun RestaurantList(restaurantViewModel: RestaurantViewModel, navController: NavHostController) {
    val restaurants by restaurantViewModel.searchRestaurants.collectAsState()
    LazyColumn {
        items(restaurants) { restaurant ->
            RestaurantItem(
                restaurant,
                modifier = Modifier.clickable {
                    restaurantViewModel.setSelectedRestaurant(restaurant)
                    navController.navigate("Detail")
                },
                onFavoriteClick = {
                    restaurantViewModel.updateFavoriteRestaurant(restaurant)
                },
                isFavorite = restaurant.favorite
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.DarkGray)
        }
    }
}

@Composable
fun ProductList(productViewModel: ProductViewModel, restaurantViewModel: RestaurantViewModel, navController: NavHostController) {
    val products by productViewModel.searchProducts.collectAsState()
    val restaurants by restaurantViewModel.restaurantsState.collectAsState()

    LazyColumn {
        items(products) { product ->
            ProductItem(product, Modifier.background(MaterialTheme.colorScheme.background).clickable {
                restaurantViewModel.setSelectedRestaurant(restaurants.find { it.id == product.restaurantId }!!)
                navController.navigate("Detail")
            })
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.DarkGray)
        }
    }

}

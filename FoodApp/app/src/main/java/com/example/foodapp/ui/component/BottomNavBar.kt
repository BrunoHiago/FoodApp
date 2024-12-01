package com.example.foodapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodapp.viewmodel.RestaurantViewModel

@Composable
fun BottomNavBar(navController: NavHostController, restaurantViewModel: RestaurantViewModel) {
    var selectedItem by remember { mutableIntStateOf(0) }

    val items = listOf("Inicio", "Pesquisar", "Favoritos", "Perfil")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Search,
        Icons.Default.Favorite,
        Icons.Default.Person
    )

    LaunchedEffect(navController.currentDestination) {
        selectedItem = when (navController.currentDestination?.route) {
            "Home" -> 0
            "Search" -> 1
            "Favorite" -> 2
            "Profile" -> 3
            else -> 0
        }
    }

    Box {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            icons[index],
                            contentDescription = item,
                            tint = if (selectedItem == index) MaterialTheme.colorScheme.primary else Color.Black
                        )
                    },
                    label = { Text(text = item) },
                    modifier = Modifier.background(Color.Transparent),
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        when (index) {
                            0 -> navController.navigate("home")
                            1 -> {
                                navController.navigate("search")
                                restaurantViewModel.setSelectedCategory("")
                            }
                            2 -> navController.navigate("favorite")
                            3 -> navController.navigate("profile")
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Color.Black,
                        unselectedTextColor = Color.Black
                    )
                )
            }


        }
    }


}
package com.example.foodapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodapp.data.model.Restaurant
import com.example.foodapp.ui.component.FavoriteItem
import com.example.foodapp.ui.component.RestaurantItem
import com.example.foodapp.utils.Category
import com.example.foodapp.utils.CategoryItem
import com.example.foodapp.viewmodel.RestaurantViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(restaurantViewModel: RestaurantViewModel, navController: NavHostController) {
    val restaurants by restaurantViewModel.restaurantsState.collectAsState()

    LaunchedEffect(Unit) {
        restaurantViewModel.getAllRestaurants()
        restaurantViewModel.getFavoriteRestaurants()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 16.dp, start = 16.dp, top = 16.dp)
    ) {
        LazyColumn {
            item {

                CategorySection(
                    modifier = Modifier,
                    restaurantViewModel = restaurantViewModel,
                    navController = navController
                )
                Spacer(modifier = Modifier.height(16.dp))

                FavoriteSection(restaurants, navController, restaurantViewModel)

                Spacer(modifier = Modifier.height(16.dp))

                Column {
                    Text(
                        "Melhores Avaliações",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                }
            }

            items(restaurants) { restaurant ->
                RestaurantItem(
                    restaurant = restaurant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .clickable {
                            restaurantViewModel.setSelectedRestaurant(restaurant)
                            navController.navigate("Detail")
                        },
                    onFavoriteClick = {
                        restaurantViewModel.updateFavoriteRestaurant(restaurant)
                    },
                    isFavorite = restaurant.favorite
                )
            }
        }
    }
}


@Composable
fun CategorySection(
    modifier: Modifier,
    restaurantViewModel: RestaurantViewModel,
    navController: NavHostController
) {
    val categories = Category.entries.map { category ->
        CategoryItem(category.displayName, category.imageRes)
    }
    val categoryColors = listOf(
        Color(0xFFF44336),
        Color(0xFFE91E63),
        Color(0xFF9C27B0),
        Color(0xFF673AB7),
        Color(0xFF3F51B5),
        Color(0xFF2196F3),
        Color(0xFF03A9F4),
        Color(0xFF00BCD4),
        Color(0xFF009688),
        Color(0xFF4CAF50),
        Color(0xFF8BC34A),
        Color(0xFFCDDC39),
        Color(0xFFFFEB3B),
    )

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Text(
                "Categorias",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)

            )
        }
        LazyRow {
            items(categories) { category ->
                CategoryCard(
                    category = category,
                    backgroundColor = categoryColors[categories.indexOf(category)],
                    modifier = Modifier.clickable {
                        restaurantViewModel.setSelectedCategory(category.name)
                        navController.navigate("Search") {
                            popUpTo("Search") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryCard(category: CategoryItem, backgroundColor: Color, modifier: Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .padding(end = 8.dp)
            .width(100.dp)
            .height(80.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.White)
            ) {
                val width = size.width
                val height = size.height

                drawPath(
                    path = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(width, 0f)
                        lineTo(width, height / 2)
                        lineTo(0f, height * 0.7f)
                        close()
                    },
                    color = backgroundColor
                )
            }

            Icon(
                painter = painterResource(id = category.image),
                contentDescription = category.name,
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.TopCenter)
                    .padding(top = 2.dp),
                tint = Color.Unspecified
            )

            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 3.dp, top = 8.dp)
            )
        }
    }
}


@Composable
fun FavoriteSection(
    restaurantList: List<Restaurant>,
    navController: NavHostController,
    restaurantViewModel: RestaurantViewModel
) {

    val favoriteRestaurants = restaurantList.filter { it.favorite }.take(6)

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Favoritos",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.weight(1f))
            TextButton(
                onClick = { navController.navigate("Favorite") },
            ) {
                Text("Ver mais")
            }
        }

        LazyRow {
            if (favoriteRestaurants.isEmpty()) {
                item {
                    Text(
                        "Nenhum favorito",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(favoriteRestaurants) { favorite ->
                    FavoriteItem(favorite, Modifier.clickable {
                        restaurantViewModel.setSelectedRestaurant(favorite)
                        navController.navigate("Detail")

                    })


                }
            }
        }
    }
}



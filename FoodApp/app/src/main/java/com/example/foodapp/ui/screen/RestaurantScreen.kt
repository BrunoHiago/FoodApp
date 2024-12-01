package com.example.foodapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.foodapp.ui.component.ProductItem
import com.example.foodapp.ui.component.RestaurantItem
import com.example.foodapp.viewmodel.ProductViewModel
import com.example.foodapp.viewmodel.RestaurantViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun RestaurantScreen(
    restaurantViewModel: RestaurantViewModel,
    productViewModel: ProductViewModel,
    navController: NavHostController
) {

    val restaurant = restaurantViewModel.selectedRestaurant.collectAsState()
    val products = productViewModel.searchProducts.collectAsState().value.groupBy { it.category }

    var isFavorite by remember {
        mutableStateOf(
            restaurantViewModel.isFavorite(restaurant.value!!)
        )
    }

    LaunchedEffect(Unit) {
        productViewModel.searchProductsForRestaurant(restaurant.value?.id ?: "")
    }
    Box(modifier = Modifier.fillMaxSize()) {
        // Imagem de fundo fixa
        AsyncImage(
            restaurant.value?.photo,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp) // Altura que será transparente
                        .background(Color.Transparent)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(50.dp)
                            .clickable {
                                navController.popBackStack()
                            })
                }
            }

            // Segunda seção: Informações do restaurante
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    if (restaurant.value != null) {
                        RestaurantItem(
                            restaurant = restaurant.value!!,
                            modifier = Modifier.fillMaxWidth(),
                            onFavoriteClick = {
                                CoroutineScope(Dispatchers.Main).launch {
                                    restaurantViewModel.updateFavoriteRestaurant(restaurant.value!!)
                                    restaurantViewModel.setSelectedRestaurant(restaurant.value!!)
                                    isFavorite = !isFavorite
                                }
                            },
                            isFavorite = isFavorite
                        )
                    }

                    Text(
                        text = restaurant.value?.description ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically

                    ) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = (restaurant.value?.address
                                ?: "") + " - " + (restaurant.value?.cep ?: ""),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.DarkGray,
                        )
                    }
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically

                    ) {
                        Icon(
                            Icons.Default.Phone,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = (restaurant.value?.phone ?: ""),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.DarkGray,
                        )
                    }
                    HorizontalDivider(color = Color.DarkGray, thickness = 1.dp)
                }
            }

            products.forEach { (category, productList) ->
                item {
                    Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                        Text(
                            text = category.lowercase().replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .background(MaterialTheme.colorScheme.background),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                    }

                }

                itemsIndexed(productList) { index, product ->
                    ProductItem(
                        product = product,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                    )


                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)

                }
            }
        }
    }

}
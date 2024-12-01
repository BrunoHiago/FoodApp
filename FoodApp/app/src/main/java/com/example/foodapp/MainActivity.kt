package com.example.foodapp

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodapp.ui.component.BottomNavBar
import com.example.foodapp.ui.screen.FavoriteScreen
import com.example.foodapp.ui.screen.HomeScreen
import com.example.foodapp.ui.screen.LoadingScreen
import com.example.foodapp.ui.screen.LoginScreen
import com.example.foodapp.ui.screen.ProfileScreen
import com.example.foodapp.ui.screen.RestaurantScreen
import com.example.foodapp.ui.screen.SearchScreen
import com.example.foodapp.ui.theme.FoodAppTheme
import com.example.foodapp.utils.TokenManager
import com.example.foodapp.viewmodel.ProductViewModel
import com.example.foodapp.viewmodel.RestaurantViewModel
import com.example.foodapp.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodAppTheme {

                val navController = rememberNavController()
                var showFab by rememberSaveable { mutableStateOf(true) }
                val applicationContext = LocalContext.current

                val userViewModel = UserViewModel(applicationContext)
                val restaurantViewModel = RestaurantViewModel(applicationContext, userViewModel)
                val productViewModel = ProductViewModel(applicationContext)


                lifecycleScope.launch {
                    TokenManager.isTokenValid(applicationContext).collect { isValid ->
                        if (isValid) {
                            navController.navigate("Loading") {
                                popUpTo("login") { inclusive = true }
                            }
                            delay(3000)
                            navController.navigate("home") {
                                popUpTo("loading") { inclusive = true }
                            }
                        } else {
                            navController.navigate("loading") {
                                popUpTo("home") { inclusive = true }
                            }
                            delay(3000)
                            navController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                }

                Scaffold(
                    bottomBar = { if (showFab) BottomNavBar(navController, restaurantViewModel) },
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "loading",
                        ) {
                            composable("loading") {
                                showFab = false
                                LoadingScreen()
                            }
                            composable("login") {
                                showFab = false
                                LoginScreen(userViewModel)
                            }
                            composable("home") {
                                showFab = true
                                HomeScreen(restaurantViewModel, navController)
                            }
                            composable("Search") {
                                showFab = true
                                SearchScreen(restaurantViewModel, productViewModel, navController)
                            }
                            composable("Favorite") {
                                showFab = true
                                FavoriteScreen(restaurantViewModel, navController)
                            }
                            composable("Detail") {
                                showFab = false
                                RestaurantScreen(
                                    restaurantViewModel = restaurantViewModel,
                                    productViewModel = productViewModel,
                                    navController = navController
                                )
                            }
                            composable("Profile") {
                                showFab = true
                                ProfileScreen(userViewModel)
                            }
                        }
                    }


                }
            }
        }
    }
}


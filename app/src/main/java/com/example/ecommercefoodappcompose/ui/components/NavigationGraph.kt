package com.example.ecommercefoodappcompose.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommercefoodappcompose.ui.screens.CartScreen
import com.example.ecommercefoodappcompose.ui.screens.FoodItemDetailsScreen
import com.example.ecommercefoodappcompose.ui.screens.HomeScreen
import com.example.ecommercefoodappcompose.ui.screens.LoginScreen
import com.example.ecommercefoodappcompose.ui.screens.SplashScreen
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel

@Composable
fun NavigationGraph(modifier: Modifier, foodViewModel: FoodViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreen {
                navController.popBackStack()
                navController.navigate("login_screen")
            }
        }

        composable("login_screen") {
            LoginScreen(navController)
        }

        composable("home_screen") {
            HomeScreen(navController, foodViewModel)
        }

        composable("food_item_details") {
            val selectedFoodItem by foodViewModel.selectedFoodItem.observeAsState()

            if (selectedFoodItem != null) {
                FoodItemDetailsScreen(selectedFoodItem!!, navController)
            }
        }
        composable("cart_screen") {
            CartScreen(foodViewModel.cartItems.value!!)
        }
    }
}

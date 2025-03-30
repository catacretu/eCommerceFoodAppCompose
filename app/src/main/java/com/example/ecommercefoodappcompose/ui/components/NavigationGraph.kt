package com.example.ecommercefoodappcompose.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommercefoodappcompose.ui.screens.HomeScreen
import com.example.ecommercefoodappcompose.ui.screens.LoginScreen
import com.example.ecommercefoodappcompose.ui.screens.SplashScreen

@Composable

fun NavigationGraph(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = "splash_screen"
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
            HomeScreen(navController)
        }

//        composable("recipe_details") {
//            val selectedItem by itemViewModel.selectedItem.observeAsState()
//
//            if (selectedItem != null) {
//                ItemDetailsScreen(selectedItem!!) {
//                    navController.popBackStack()
//                }
//            }
//        }
    }
}
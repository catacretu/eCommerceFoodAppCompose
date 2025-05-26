package com.example.ecommercefoodappcompose.ui.components

import android.app.Activity
import android.content.Context
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ecommercefoodappcompose.ui.screens.CartScreen
import com.example.ecommercefoodappcompose.ui.screens.CheckoutScreen
import com.example.ecommercefoodappcompose.ui.screens.FavouritesScreen
import com.example.ecommercefoodappcompose.ui.screens.FoodItemDetailsScreen
import com.example.ecommercefoodappcompose.ui.screens.HomeScreen
import com.example.ecommercefoodappcompose.ui.screens.LoginScreen
import com.example.ecommercefoodappcompose.ui.screens.MoreScreen
import com.example.ecommercefoodappcompose.ui.screens.RecipeDetailsScreen
import com.example.ecommercefoodappcompose.ui.screens.RecipesScreen
import com.example.ecommercefoodappcompose.ui.screens.ShippingScreen
import com.example.ecommercefoodappcompose.ui.screens.SplashScreen
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel
import com.example.ecommercefoodappcompose.ui.viewmodel.RecipeViewModel

@Composable
fun NavigationGraph(
    modifier: Modifier,
    activity: Activity,
    context: Context,
    foodViewModel: FoodViewModel,
    recipeViewModel: RecipeViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash_screen",
        enterTransition = { fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(500)) },
        popEnterTransition = { fadeIn(animationSpec = tween(500)) },
        popExitTransition = { fadeOut(animationSpec = tween(500)) }
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
            HomeScreen(
                navController,
                activity,
                foodViewModel
            )
        }

        composable("food_item_details") {
            val selectedFoodItem by foodViewModel.selectedFoodItem.observeAsState()

            if (selectedFoodItem != null) {
                FoodItemDetailsScreen(
                    activity,
                    context,
                    selectedFoodItem!!,
                    navController,
                    foodViewModel
                )
            }
        }

        composable("cart_screen") {
            CartScreen(
                activity,
                navController,
                foodViewModel
            )
        }

        composable("recipes_screen") {
            RecipesScreen(
                navController,
                foodViewModel,
                recipeViewModel
            )
        }

        composable(
            route = "recipe_item_details?isDefaultRecipe={isDefaultRecipe}",
            arguments = listOf(
                navArgument("isDefaultRecipe") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )) {backStackEntry ->
            val isDefaultRecipe = backStackEntry.arguments?.getBoolean("isDefaultRecipe") ?: false
            val selectedRecipe by recipeViewModel.selectedRecipe.observeAsState()

            if (selectedRecipe != null) {
                RecipeDetailsScreen(selectedRecipe!!, recipeViewModel, isDefaultRecipe) {
                    navController.popBackStack()
                }
            }
        }

        composable("favourites_screen") {
            FavouritesScreen(
                activity,
                navController,
                foodViewModel
            )
        }

        composable("more_screen") {
            MoreScreen(navController)
        }

        composable("shipping_screen") {
            ShippingScreen(
                navController,
                foodViewModel
            )
        }

        composable("checkout_screen") {
            CheckoutScreen(
                activity,
                navController,
                foodViewModel
            )
        }
    }
}

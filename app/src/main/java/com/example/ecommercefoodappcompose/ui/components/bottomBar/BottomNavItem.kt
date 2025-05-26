package com.example.ecommercefoodappcompose.ui.components.bottomBar

import com.example.ecommercefoodappcompose.R

sealed class BottomNavItem(val title: String, val icon: Int, val route: String) {
    data object Home : BottomNavItem("Home", R.drawable.ic_home_24, "home_screen")
    data object Cart : BottomNavItem("Cart", R.drawable.ic_cart_24, "cart_screen")
    data object Recipes : BottomNavItem("Recipes", R.drawable.ic_search_24, "recipes_screen")
    data object Favourite : BottomNavItem("Favourite", R.drawable.ic_person_24, "favourites_screen")
    data object More : BottomNavItem("More", R.drawable.ic_apps_24, "more_screen")
}

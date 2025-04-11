package com.example.ecommercefoodappcompose.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ecommercefoodappcompose.data.local.model.FoodItem

@Composable
fun CartScreen(orderItems: List<FoodItem>) {
    FoodList(
        orderItems,
        modifier = Modifier,
        onFoodItemClick = {}
    )
}

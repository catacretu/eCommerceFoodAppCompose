package com.example.ecommercefoodappcompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.ui.components.FoodCartItem

@Composable
fun CartScreen(orderItems: List<FoodItem>) {
    OrderCartList(
        orderItems,
        modifier = Modifier,
        onFoodItemClick = {}
    )
}

@Composable
fun OrderCartList(
    foodItems: List<FoodItem>,
    modifier: Modifier,
    onFoodItemClick: (FoodItem) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .wrapContentHeight()
            .padding(top = 80.dp, start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(foodItems) { foodItem ->
            FoodCartItem(foodItem)
        }
    }
}

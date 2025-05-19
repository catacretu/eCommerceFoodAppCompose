package com.example.ecommercefoodappcompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecommercefoodappcompose.data.local.model.FoodItem

@Composable
fun FoodList(
    foodItems: List<FoodItem>,
    modifier: Modifier,
    onFoodItemClick: (FoodItem) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(foodItems) { foodItem ->
            FoodCartItem(
                foodItem,
                onFoodItemClick = { selectedFoodItem ->
                    onFoodItemClick(selectedFoodItem)
                }
            )
        }
    }
}

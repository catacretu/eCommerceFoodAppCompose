package com.example.ecommercefoodappcompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecommercefoodappcompose.R.drawable.ic_launcher_foreground
import com.example.ecommercefoodappcompose.ui.components.FoodCartItem

@Composable
fun HomeScreen(navController: NavController) {
    val items = listOf(
        Pair(ic_launcher_foreground, "Product 1" to "$10.00"),
        Pair(ic_launcher_foreground, "Product 2" to "$20.00"),
        Pair(ic_launcher_foreground, "Product 3" to "$30.00"),
        Pair(ic_launcher_foreground, "Product 4" to "$40.00"),
        Pair(ic_launcher_foreground, "Product 5" to "$50.00"),
        Pair(ic_launcher_foreground, "Product 6" to "$60.00"),
        Pair(ic_launcher_foreground, "Product 6" to "$60.00"),
        Pair(ic_launcher_foreground, "Product 6" to "$60.00")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(top = 80.dp, start = 30.dp, end = 30.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { (imageRes, product) ->
            FoodCartItem(imageRes = imageRes, title = product.first, price = product.second)
        }
    }
}

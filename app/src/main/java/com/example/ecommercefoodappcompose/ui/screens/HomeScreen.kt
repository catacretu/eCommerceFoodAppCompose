package com.example.ecommercefoodappcompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommercefoodappcompose.R
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.ui.components.FoodCartItem
import com.example.ecommercefoodappcompose.ui.components.HandleLoadingState
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.theme.inversePrimaryDark
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    foodViewModel: FoodViewModel
) {
    val foodItems = foodViewModel.foodItems.observeAsState(initial = emptyList())
    val isLoading = foodViewModel.isLoading.observeAsState(initial = false)
    val showLoading = remember { mutableStateOf(false) }
    val minLoadingTime = 1000L
    var loadingStartTime = remember { mutableLongStateOf(0L) }

    HandleLoadingState(
        isLoading,
        showLoading,
        minLoadingTime,
        loadingStartTime
    )

    if (showLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (foodItems.value.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.network_data_error),
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    style = AppTypography.headlineMedium,
                    color = inversePrimaryDark
                )
            }
        } else {
            FoodList(
                foodItems.value,
                onFoodItemClick = { selectedFoodItem ->
                    foodViewModel.selectFoodItem(selectedFoodItem)
                    navController.navigate("food_item_details")
                }
            )
        }
    }
}

@Composable
fun FoodList(
    foodItems: List<FoodItem>,
    onFoodItemClick: (FoodItem) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 80.dp, start = 30.dp, end = 30.dp, bottom = 80.dp),
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

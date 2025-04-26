package com.example.ecommercefoodappcompose.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommercefoodappcompose.R
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.ui.components.FoodCartItem
import com.example.ecommercefoodappcompose.ui.components.HandleLoadingState
import com.example.ecommercefoodappcompose.ui.components.bottomBar.BottomNavigationBar
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.theme.inversePrimaryDark
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    activity: Activity,
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
            foodViewModel.loadCartItems(activity)
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Home Screen",
                                style = AppTypography.titleLarge
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = inversePrimaryDark,
                            titleContentColor = Color.White
                        )
                    )
                },
                bottomBar = {
                    BottomNavigationBar(navController)
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    FoodList(
                        foodItems.value,
                        modifier = Modifier.weight(1f),
                        onFoodItemClick = { selectedFoodItem ->
                            foodViewModel.selectFoodItem(selectedFoodItem)
                            navController.navigate("food_item_details")
                        }
                    )
                    // as example for separate component
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(end = 40.dp),
//                        horizontalArrangement = Arrangement.End
//                    ) {
//                        GradientButton(
//                            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
//                            textButton = "CART",
//                            onClick = { navController.navigate("cart_screen") }
//                        )
//                    }
                }
            }
        }
    }
}

@Composable
fun FoodList(
    foodItems: List<FoodItem>,
    modifier: Modifier,
    onFoodItemClick: (FoodItem) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .wrapContentHeight()
            .padding(top = 40.dp, start = 30.dp, end = 30.dp),
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

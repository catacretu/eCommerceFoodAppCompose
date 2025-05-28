package com.example.ecommercefoodappcompose.ui.screens

import android.app.Activity
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommercefoodappcompose.R
import com.example.ecommercefoodappcompose.ui.components.FilterItem
import com.example.ecommercefoodappcompose.ui.components.FoodList
import com.example.ecommercefoodappcompose.ui.components.HandleLoadingState
import com.example.ecommercefoodappcompose.ui.components.HomeSearchBar
import com.example.ecommercefoodappcompose.ui.components.SortDropdownMenu
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
    val filteredFoodItems = foodViewModel.filteredFoodItems.observeAsState(initial = emptyList())
    val isLoading = foodViewModel.isLoading.observeAsState(initial = false)
    var isDropdownExpanded by rememberSaveable { mutableStateOf(false) }
    val showLoading = rememberSaveable { mutableStateOf(false) }
    val searchQuery = foodViewModel.searchQuery
    val minLoadingTime = 1000L
    val focusManager = LocalFocusManager.current
    var loadingStartTime = rememberSaveable { mutableLongStateOf(0L) }
    val filters = listOf(
        "Vegetables" to Icons.Default.ShoppingCart,
        "Fruits" to Icons.Default.AddCircle,
        "Meat" to Icons.Default.Lock,
        "Dairy" to Icons.Default.Lock,
        "Others" to Icons.Default.Lock
    )

    HandleLoadingState(
        isLoading,
        showLoading,
        minLoadingTime,
        loadingStartTime
    )

    LaunchedEffect(Unit) {
        foodViewModel.loadCartItems(activity)
        foodViewModel.loadFavouritesItems(activity)
    }

    if (showLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val displayedFoodList = filteredFoodItems.value
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
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focusManager.clearFocus()
                            })
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        HomeSearchBar(
                            viewModel = foodViewModel,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                        )
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(top = 3.dp)
                        ) {
                            IconButton(onClick = { isDropdownExpanded = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_sort_24),
                                    contentDescription = "Sort Button"
                                )
                            }
                            SortDropdownMenu(
                                isExpanded = isDropdownExpanded,
                                onDismissRequest = { isDropdownExpanded = false },
                                selectedOption = foodViewModel.sortOption,
                                onSortSelected = { selectedSort ->
                                    foodViewModel.updateSortOption(selectedSort)
                                }
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp)
                            .padding(top = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        filters.forEach { (label, icon) ->
                            FilterItem(
                                icon = icon,
                                label = label,
                                isSelected = label in foodViewModel.activeFilters,
                                onClick = {
                                    foodViewModel.toggleCategoryFilter(label)
                                }
                            )
                        }
                    }

                    if (searchQuery.isNotEmpty() && displayedFoodList.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No results!",
                                fontSize = 30.sp,
                                textAlign = TextAlign.Center,
                                style = AppTypography.headlineMedium,
                                color = inversePrimaryDark
                            )
                        }
                    } else {
                        FoodList(
                            displayedFoodList,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 30.dp)
                                .padding(top = 10.dp, bottom = 15.dp),
                            onFoodItemClick = { selectedFoodItem ->
                                foodViewModel.selectFoodItem(selectedFoodItem)
                                navController.navigate("food_item_details")
                            }
                        )
                    }
                }
            }
        }
    }
}

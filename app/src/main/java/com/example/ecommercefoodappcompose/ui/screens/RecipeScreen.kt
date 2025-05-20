package com.example.ecommercefoodappcompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecommercefoodappcompose.ui.components.FoodList
import com.example.ecommercefoodappcompose.ui.components.RecipeSearchBar
import com.example.ecommercefoodappcompose.ui.components.bottomBar.BottomNavigationBar
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.theme.inversePrimaryDark
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel
import com.example.ecommercefoodappcompose.ui.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    navController: NavController,
    recipeViewModel: RecipeViewModel,
    foodViewModel: FoodViewModel
) {
    val recipesItems = recipeViewModel.recipeItems.observeAsState(initial = emptyList())
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ai Search Screen",
                        style = AppTypography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = inversePrimaryDark,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            RecipeSearchBar(
                searchQuery = recipeViewModel.searchQuery,
                onQueryChanged = { recipeViewModel.searchQuery = it },
                viewModel = recipeViewModel,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp)
            )

            FoodList(
                recipesItems.value,
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

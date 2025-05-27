package com.example.ecommercefoodappcompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommercefoodappcompose.ui.components.RecipeList
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.viewmodel.RecipeViewModel

@Composable
fun OurRecipesScreen(navController: NavController, recipeViewModel: RecipeViewModel) {
    val defaultRecipes = recipeViewModel.defaultRecipes.observeAsState(initial = emptyList())
    val foodItems by recipeViewModel.foodItems.observeAsState(initial = emptyList())
    Column(
        modifier = Modifier
            .wrapContentSize()
    ) {
        Text(
            "You can try!",
            fontSize = 25.sp,
            style = AppTypography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(start = 25.dp, top = 25.dp)
        )

        RecipeList(
            recipeList = defaultRecipes.value,
            onRecipeSelected = { selectedRecipe ->
                recipeViewModel.selectRecipe(selectedRecipe)
                navController.navigate("recipe_item_details?isDefaultRecipe=true")
            },
            onFavoriteClick = {},
            onDislikeClicked = {},
            isDefaultValues = true,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

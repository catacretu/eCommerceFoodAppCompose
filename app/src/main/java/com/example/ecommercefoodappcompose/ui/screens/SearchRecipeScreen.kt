package com.example.ecommercefoodappcompose.ui.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommercefoodappcompose.R
import com.example.ecommercefoodappcompose.ui.components.RecipeList
import com.example.ecommercefoodappcompose.ui.components.RecipeSearchBar
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.viewmodel.RecipeViewModel

@Composable
fun SearchRecipeScreen(
    navController: NavController,
    recipeViewModel: RecipeViewModel
) {
    val recipesItems = recipeViewModel.recipeItems.observeAsState(initial = emptyList())
    val favouriteRecipes = recipeViewModel.favouriteRecipes.observeAsState(initial = emptyList())
    val isLoading by recipeViewModel.isLoading.observeAsState(false)
    val focusManager = LocalFocusManager.current
    val searchQuery = recipeViewModel.searchQuery

    Column(
        modifier = Modifier
            .wrapContentSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        RecipeSearchBar(
            searchQuery = recipeViewModel.searchQuery,
            onQueryChanged = { recipeViewModel.searchQuery = it },
            viewModel = recipeViewModel,
            modifier = Modifier
                .padding(horizontal = 25.dp)
                .padding(top = 20.dp)
        )

        if (searchQuery.isEmpty() && recipeViewModel.shouldClearResults) {
            recipeViewModel.clearAllRecipes()
        }

        val displayedRecipes = if (searchQuery.isEmpty()) favouriteRecipes else recipesItems
        Text(
            if (!isLoading && searchQuery.isEmpty()) "Favourites" else "Suggested Recipes",
            fontSize = 25.sp,
            style = AppTypography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(start = 25.dp, top = 15.dp)
        )

        if (displayedRecipes.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(
                        if (searchQuery.isEmpty()) {
                            R.string.no_favorites_available_txt
                        } else {
                            R.string.no_recipes_available_txt
                        }
                    ),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            RecipeList(
                recipeList = displayedRecipes.value,
                onRecipeSelected = { selectedRecipe ->
                    recipeViewModel.selectRecipe(selectedRecipe)
                    navController.navigate("recipe_item_details")
                },
                onFavoriteClick = { recipe ->
                    recipeViewModel.toggleFavorite(recipe)
                },
                onDislikeClicked = {
                    recipeViewModel.searchRecipes(searchQuery, true)
                },
                isLoading = isLoading,
                searchQuery = searchQuery,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

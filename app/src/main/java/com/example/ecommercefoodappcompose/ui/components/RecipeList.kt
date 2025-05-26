package com.example.ecommercefoodappcompose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ecommercefoodappcompose.data.local.model.RecipeItem
import com.example.ecommercefoodappcompose.ui.theme.inversePrimaryDark

@Composable
fun RecipeList(
    recipeList: List<RecipeItem>,
    onRecipeSelected: (RecipeItem) -> Unit,
    onFavoriteClick: (RecipeItem) -> Unit,
    onDislikeClicked: () -> Unit,
    isLoading: Boolean = false,
    searchQuery: String = "",
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .padding(horizontal = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(recipeList) { recipe ->
            RecipeCard(
                recipe,
                onFavoriteClick = { updatedRecipe ->
                    onFavoriteClick(updatedRecipe)
                },
                onRecipeClick = { selectedRecipe ->
                    onRecipeSelected(selectedRecipe)
                }
            )
        }
        if (!isLoading && searchQuery.isNotEmpty()) {
            item {
                Button(
                    onClick = { onDislikeClicked() },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = inversePrimaryDark)
                ) {
                    Text(text = "I don’t like these", color = Color.White)
                }
            }
        }
    }
}

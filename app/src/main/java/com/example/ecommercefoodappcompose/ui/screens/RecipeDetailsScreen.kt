package com.example.ecommercefoodappcompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecommercefoodappcompose.R
import com.example.ecommercefoodappcompose.data.local.model.RecipeItem
import com.example.ecommercefoodappcompose.ui.components.FoodCartItem
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel
import com.example.ecommercefoodappcompose.ui.viewmodel.RecipeViewModel

@Composable
fun RecipeDetailsScreen(
    navController: NavController,
    recipe: RecipeItem,
    foodViewModel: FoodViewModel,
    recipeViewModel: RecipeViewModel,
    isDefaultRecipe: Boolean,
    onBackClick: () -> Unit
) {
    val isFavourite = recipeViewModel.isFavourite.value
    val ingredientsList = recipeViewModel.ingredientsList.observeAsState(initial = emptyList())
    LaunchedEffect(recipe.id) {
        recipeViewModel.loadFavouriteStatus(recipe.id)
    }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(top = 45.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { onBackClick() },
                modifier = Modifier
                    .size(32.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }

            if (!isDefaultRecipe) {
                IconButton(
                    onClick = { recipeViewModel.toggleFavorite(recipe) },
                    modifier = Modifier
                        .size(32.dp)
                ) {
                    Icon(
                        if (isFavourite == true) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Filled.FavoriteBorder
                        },
                        contentDescription =
                        if (isFavourite == true) "Favourite Selected" else "Favourite Unselected"
                    )
                }
            }
        }

        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = "Recipe Image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 20.dp)
                .height(250.dp),
            placeholder = painterResource(R.drawable.food_placeholder),
            error = painterResource(R.drawable.food_placeholder)
        )

        Text(
            text = recipe.title,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier
                .padding(top = 15.dp)
                .padding(start = 20.dp)
        )
        val formattedTime = recipe.time.replace(Regex("\\D+"), "") + " min."

        Text(
            text = formattedTime,
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 20.dp)
        )

        Text(
            text = "Ingredients:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 20.dp)
        )

        recipe.ingredients.forEach {
            Text(text = "• $it", fontSize = 16.sp, modifier = Modifier.padding(start = 20.dp))
        }
        Text(
            text = "Instructions:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 20.dp)
        )
        val formattedInstructionsText = recipe.instructions.replace(Regex("\\\\n|\\\\r\\\\n"), "\n")
        Text(
            text = formattedInstructionsText,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )
        if (ingredientsList.value.isNotEmpty()) {
            Text(
                text = stringResource(R.string.start_this_recipe_with_these_txt),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(start = 20.dp)
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(ingredientsList.value) { ingredient ->
                    FoodCartItem(
                        ingredient,
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

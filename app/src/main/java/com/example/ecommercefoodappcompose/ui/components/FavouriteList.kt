package com.example.ecommercefoodappcompose.ui.components

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommercefoodappcompose.R
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel

@Composable
fun FavouriteList(
    activity: Activity,
    foodViewModel: FoodViewModel,
    navController: NavController
) {
    val sharedPref = activity.getSharedPreferences("shopping_cart", Context.MODE_PRIVATE)
    val favouritesItems by foodViewModel.favouritesItems.observeAsState(initial = emptyList())

    if (favouritesItems.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.empty_favourites_msg),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                style = AppTypography.headlineMedium,
                color = MaterialTheme.colorScheme.inversePrimary
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 40.dp, start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = favouritesItems, key = { it.id }) { favouriteItem ->
                FoodRowItem(
                    activity,
                    favouriteItem,
                    sharedPref,
                    onAddItem = { foodItem ->
                        foodViewModel.addCartItem(foodItem)
                    },
                    onRemoveItem = { foodItemId ->
                        foodViewModel.removeCartItem(foodItemId)
                    },
                    onClickItem = { selectedFoodItem ->
                        foodViewModel.selectFoodItem(selectedFoodItem)
                        navController.navigate("food_item_details")
                    },
                    isFavourite = true
                )
            }
        }
    }
}

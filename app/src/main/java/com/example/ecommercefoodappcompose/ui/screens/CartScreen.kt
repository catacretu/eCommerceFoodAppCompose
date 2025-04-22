package com.example.ecommercefoodappcompose.ui.screens

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommercefoodappcompose.R
import com.example.ecommercefoodappcompose.ui.components.FoodCartItem
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.theme.inversePrimaryDark
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel

@Composable
fun CartScreen(
    activity: Activity,
    foodViewModel: FoodViewModel
) {
    OrderCartList(
        activity,
        foodViewModel,
        modifier = Modifier
    )
}

@Composable
fun OrderCartList(
    activity: Activity,
    foodViewModel: FoodViewModel,
    modifier: Modifier
) {
    val sharedPref = activity.getSharedPreferences("shopping_cart", Context.MODE_PRIVATE)
    val cartItems by foodViewModel.cartItems.observeAsState(initial = emptyList())
    if (cartItems.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.empty_cart_msg),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                style = AppTypography.headlineMedium,
                color = inversePrimaryDark
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .wrapContentHeight()
                .padding(top = 80.dp, start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = cartItems,
                key = { it.id }
            ) { cartItem ->
                FoodCartItem(
                    cartItem,
                    sharedPref,
                    onRemoveItem = { foodItemToRemove ->
                        foodViewModel.removeCartItem(foodItemToRemove.id)
                    }
                )
            }
        }
    }
}

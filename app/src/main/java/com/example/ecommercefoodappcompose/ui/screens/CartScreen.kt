package com.example.ecommercefoodappcompose.ui.screens

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.ecommercefoodappcompose.ui.components.FoodRowItem
import com.example.ecommercefoodappcompose.ui.components.GradientButton
import com.example.ecommercefoodappcompose.ui.components.bottomBar.BottomNavigationBar
import com.example.ecommercefoodappcompose.ui.components.extractPrice
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.theme.inversePrimaryDark
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    activity: Activity,
    navController: NavController,
    foodViewModel: FoodViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cart Screen",
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
            val totalAmount: MutableState<Int> = remember { mutableIntStateOf(0) }
            OrderCartList(
                activity,
                foodViewModel,
                navController,
                totalAmount
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total: ${totalAmount.value} lei",
                    modifier = Modifier.padding(start = 30.dp, top = 28.dp, bottom = 85.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                GradientButton(
                    modifier = Modifier.padding(top = 15.dp, bottom = 85.dp),
                    textButton = "Checkout",
                    onClick = { navController.navigate("cart_screen") }
                )
            }
        }
    }
}

@Composable
fun OrderCartList(
    activity: Activity,
    foodViewModel: FoodViewModel,
    navController: NavController,
    totalAmount: MutableState<Int>
) {
    val sharedPref = activity.getSharedPreferences("shopping_cart", Context.MODE_PRIVATE)
    val cartItems by foodViewModel.cartItems.observeAsState(initial = emptyList())

    val quantities = remember(cartItems) {
        cartItems.associate { cartItem ->
            cartItem.id to mutableIntStateOf(sharedPref.getInt(cartItem.id.toString(), 1))
        }
    }

    LaunchedEffect(quantities.values.map { it.intValue }) {
        totalAmount.value = cartItems.sumOf { item ->
            val quantity = quantities[item.id]?.intValue ?: 1
            quantity * extractPrice(item.price)
        }
    }

    if (cartItems.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 40.dp, start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = cartItems, key = { it.id }) { cartItem ->
                val itemQuantity = quantities[cartItem.id]!!
                FoodRowItem(
                    activity,
                    cartItem,
                    sharedPref,
                    itemQuantity,
                    onRemoveItem = { foodItemId ->
                        foodViewModel.removeCartItem(foodItemId)
                    },
                    onClickItem = { selectedFoodItem ->
                        foodViewModel.selectFoodItem(selectedFoodItem)
                        navController.navigate("food_item_details")
                    }
                )
            }
        }
    }
}

package com.example.ecommercefoodappcompose.ui.screens

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecommercefoodappcompose.R
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.ui.components.GradientButton
import com.example.ecommercefoodappcompose.ui.components.StarRatingBar
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel

@Composable
fun FoodItemDetailsScreen(
    activity: Activity,
    context: Context,
    foodItem: FoodItem,
    navController: NavController,
    foodViewModel: FoodViewModel
) {
    val scrollState = rememberScrollState()
    val sh = activity.getSharedPreferences("favourite", Context.MODE_PRIVATE)
    val isFavourite = remember { mutableStateOf(sh.getBoolean(foodItem.id.toString(), false)) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 55.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(start = 15.dp)
                    .size(32.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            IconButton(
                onClick = {
                    isFavourite.value = !isFavourite.value
                    addToFavourite(sh, foodViewModel, foodItem, isFavourite.value)
                },
                modifier = Modifier
                    .padding(end = 15.dp)
                    .size(32.dp)
            ) {
                Icon(
                    if (isFavourite.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription =
                    if (isFavourite.value) "Favourite Selected" else "Favourite Unselected"
                )
            }
        }
        AsyncImage(
            model = foodItem.imageUrl,
            contentDescription = foodItem.name,
            modifier = Modifier
                .height(270.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            placeholder = painterResource(id = R.drawable.food_placeholder),
            error = painterResource(id = R.drawable.food_placeholder)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = foodItem.name,
                fontSize = 23.sp,
                fontWeight = FontWeight.W900,
                color = colorResource(R.color.gray_item_title),
                style = AppTypography.bodyLarge,
                modifier = Modifier.padding(top = 25.dp, start = 27.dp)
            )
            Text(
                text = foodItem.price,
                fontSize = 15.sp,
                fontWeight = FontWeight.W600,
                color = colorResource(R.color.gray_item_title),
                style = AppTypography.bodySmall,
                modifier = Modifier
                    .padding(bottom = 3.dp, end = 40.dp)
                    .align(Alignment.Bottom)
            )
        }
        StarRatingBar(maxStars = 5, rating = foodItem.rating, onRatingChanged = {})
        Text(
            text = foodItem.description,
            fontSize = 17.sp,
            fontWeight = FontWeight.W300,
            color = colorResource(R.color.gray_item_title),
            style = AppTypography.bodyLarge,
            modifier = Modifier
                .verticalScroll(scrollState)
                .weight(1f)
                .padding(start = 25.dp, end = 25.dp, top = 15.dp, bottom = 15.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            GradientButton(
                textButton = "Add to cart",
                onClick = { addToCartBtnListener(activity, context, foodItem) }
            )
        }
    }
}

private fun addToCartBtnListener(activity: Activity, context: Context, foodItem: FoodItem) {
    val sh = activity.getSharedPreferences("shopping_cart", Context.MODE_PRIVATE)
    val foodItemId = foodItem.id.toString()
    var itemQuantity = 0
    sh.edit().apply {
        if (sh.contains(foodItemId)) {
            itemQuantity = sh.getInt(foodItemId, 0)
        }
        if (itemQuantity > 0) { // if item exists, we will increase the quantity
            itemQuantity++
            putInt(foodItemId, itemQuantity)
        } else { // if  not, we will put it in the cart
            putInt(foodItemId, 1)
        }
    }.apply()
    Toast.makeText(
        context,
        context.getString(R.string.product_added_successfully_msg),
        Toast.LENGTH_SHORT
    ).show()
}

private fun addToFavourite(
    sh: SharedPreferences,
    foodViewModel: FoodViewModel,
    foodItem: FoodItem,
    isFavourite: Boolean
) {
    val foodItemId = foodItem.id.toString()
    sh.edit().apply {
        if (sh.contains(foodItemId)) {
            remove(foodItemId)
        } else {
            putBoolean(foodItemId, true)
        }
    }.apply()
    foodViewModel.toggleFavourite(foodItem, isFavourite)
}

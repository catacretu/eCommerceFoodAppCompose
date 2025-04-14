package com.example.ecommercefoodappcompose.ui.screens

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun FoodItemDetailsScreen(foodItem: FoodItem, navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(top = 50.dp, start = 10.dp)
                .size(32.dp)
                .align(Alignment.Start)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        AsyncImage(
            model = foodItem.imageUrl,
            contentDescription = foodItem.title,
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
                text = foodItem.title,
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
                navController = navController,
                route = "cart_screen",
                textButton = "Add to cart"
            )
        }
    }
}

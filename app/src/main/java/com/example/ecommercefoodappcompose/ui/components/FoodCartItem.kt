package com.example.ecommercefoodappcompose.ui.components

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecommercefoodappcompose.R
import com.example.ecommercefoodappcompose.data.local.model.FoodItem

@Composable
fun FoodCartItem(
    foodItem: FoodItem,
    sharedPref: SharedPreferences,
    itemQuantity: MutableState<Int>,
    onRemoveItem: (FoodItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = foodItem.imageUrl,
                contentDescription = foodItem.title,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(10.dp),
                placeholder = painterResource(id = R.drawable.food_placeholder),
                error = painterResource(id = R.drawable.food_placeholder)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = foodItem.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${itemQuantity.value * extractPrice(foodItem.price)} lei",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.clickable {
                        increaseClickListener(sharedPref, foodItem, itemQuantity)
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add_btn_26),
                        contentDescription = "Add"
                    )
                }
                Text(
                    text = itemQuantity.value.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                )
                Box(
                    modifier = Modifier.clickable {
                        decreaseClickListener(
                            sharedPref,
                            foodItem,
                            itemQuantity,
                            onRemoveItem
                        )
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.remove_btn_26),
                        contentDescription = "Remove"
                    )
                }
            }
        }
    }
}

fun increaseClickListener(
    sharedPref: SharedPreferences,
    foodItem: FoodItem,
    itemQuantity: MutableState<Int>
) {
    sharedPref.edit().apply {
        putInt(foodItem.id.toString(), ++itemQuantity.value)
    }.apply()
}

fun decreaseClickListener(
    sharedPref: SharedPreferences,
    foodItem: FoodItem,
    itemQuantity: MutableState<Int>,
    onRemoveItem: (FoodItem) -> Unit
) {
    sharedPref.edit().apply {
        if (itemQuantity.value == 1) {
            onRemoveItem(foodItem)
            remove(foodItem.id.toString())
        } else {
            putInt(foodItem.id.toString(), --itemQuantity.value)
        }
    }.apply()
}

fun extractPrice(pricePerProduct: String): Int {
    val priceIndex = pricePerProduct.indexOf(" lei")
    if (priceIndex != -1) {
        return pricePerProduct.substring(0, priceIndex).toInt()
    }
    return 0
}

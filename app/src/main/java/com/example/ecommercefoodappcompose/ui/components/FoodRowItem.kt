package com.example.ecommercefoodappcompose.ui.components

import android.app.Activity
import android.content.Context
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecommercefoodappcompose.R
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.ui.theme.AppTypography

@Composable
fun FoodRowItem(
    activity: Activity,
    foodItem: FoodItem,
    sharedPref: SharedPreferences,
    itemQuantity: MutableState<Int> = mutableIntStateOf(1),
    onAddItem: (FoodItem) -> Unit = {},
    onRemoveItem: (Int) -> Unit,
    onClickItem: (FoodItem) -> Unit,
    isFavourite: Boolean = false,
    isCheckoutScreen: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .clickable {
                onClickItem(foodItem)
            },
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
                contentDescription = foodItem.name,
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
                    text = foodItem.name,
                    style = AppTypography.titleMedium
                )
                Text(
                    text = if (isFavourite) {
                        foodItem.price
                    } else if (isCheckoutScreen) {
                        "Subtotal: ${itemQuantity.value * extractPrice(foodItem.price)} lei"
                    } else {
                        "${itemQuantity.value * extractPrice(foodItem.price)} lei"
                    },
                    style = AppTypography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isFavourite) {
                    val sh =
                        activity.getSharedPreferences("shopping_cart", Context.MODE_PRIVATE)
                    val isAddedToCart =
                        remember {
                            mutableStateOf(
                                sh.contains(foodItem.id.toString())
                            )
                        }
                    Box(
                        modifier = Modifier.clickable {
                            isAddedToCart.value = !isAddedToCart.value
                            addToCartBtnListener(
                                sh,
                                foodItem,
                                onAddItem,
                                onRemoveItem,
                                isAddedToCart
                            )
                        }
                    ) {
                        Image(
                            if (isAddedToCart.value) {
                                Icons.Filled.ShoppingCart
                            } else {
                                Icons.Outlined.ShoppingCart
                            },
                            contentDescription = "Add to cart, button"
                        )
                    }
                } else if (!isCheckoutScreen) {
                    Box(
                        modifier = Modifier.clickable {
                            increaseClickListener(
                                sharedPref,
                                foodItem.id.toString(),
                                itemQuantity
                            )
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.add_btn_26),
                            contentDescription = "Add"
                        )
                    }
                    Text(
                        text = itemQuantity.value.toString(),
                        style = AppTypography.bodySmall,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )
                    Box(
                        modifier = Modifier.clickable {
                            decreaseClickListener(
                                sharedPref,
                                foodItem.id.toString(),
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
                } else {
                    Text(
                        text = "${itemQuantity.value}",
                        style = AppTypography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 15.dp)
                    )
                }
            }
        }
    }
}

fun addToCartBtnListener(
    sharedPref: SharedPreferences,
    foodItem: FoodItem,
    onAddItem: (FoodItem) -> Unit,
    onRemoveItem: (Int) -> Unit,
    isAddedToCart: MutableState<Boolean> = mutableStateOf(false)
) {
    val foodItemId = foodItem.id.toString()
    var itemQuantity = 0
    sharedPref.edit().apply {
        if (sharedPref.contains(foodItemId)) {
            itemQuantity = sharedPref.getInt(foodItemId, 0)
        }
        if (itemQuantity > 0) {
            onRemoveItem(foodItemId.toInt())
            remove(foodItemId)
            isAddedToCart.value = false
        } else { // if  not, we will put it in the cart
            putInt(foodItemId, 1)
            onAddItem(foodItem)
        }
    }.apply()
}

fun increaseClickListener(
    sharedPref: SharedPreferences,
    foodItemId: String,
    itemQuantity: MutableState<Int>
) {
    sharedPref.edit().apply {
        putInt(foodItemId, ++itemQuantity.value)
    }.apply()
}

fun decreaseClickListener(
    sharedPref: SharedPreferences,
    foodItemId: String,
    itemQuantity: MutableState<Int>,
    onRemoveItem: (Int) -> Unit
) {
    sharedPref.edit().apply {
        if (itemQuantity.value == 1) {
            onRemoveItem(foodItemId.toInt())
            remove(foodItemId)
        } else {
            putInt(foodItemId, --itemQuantity.value)
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

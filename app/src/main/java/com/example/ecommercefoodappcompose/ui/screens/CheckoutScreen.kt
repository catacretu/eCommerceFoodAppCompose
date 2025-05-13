package com.example.ecommercefoodappcompose.ui.screens

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommercefoodappcompose.ui.components.GradientButton
import com.example.ecommercefoodappcompose.ui.components.OrderCartList
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel

@Composable
fun CheckoutScreen(
    activity: Activity,
    navController: NavController,
    foodViewModel: FoodViewModel
) {
    val shippingDetails = foodViewModel.shippingDetails
    val totalAmount: MutableState<Int> = remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(
                text = "Shipping Address",
                style = AppTypography.titleMedium,
                fontWeight = FontWeight.W900,
                fontSize = 19.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 25.dp, top = 10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            GradientButton(
                modifier = Modifier.padding(end = 25.dp),
                textButton = "Change",
                onClick = {
                    //                navController.popBackStack()
                    navController.navigate("cart_screen")
                }
            )
        }
        Spacer(
            modifier = Modifier
                .padding(top = 8.dp)
                .height(2.dp)
                .fillMaxWidth()
                .background(color = Color.LightGray)
        )
        Text(
            text = shippingDetails.name,
            style = AppTypography.bodyLarge,
            modifier = Modifier.padding(top = 10.dp, start = 25.dp),
            fontWeight = FontWeight.W700,
            fontSize = 18.sp
        )
        Text(
            text = "${shippingDetails.phone}\n" +
                "${shippingDetails.email}\n" +
                "${shippingDetails.address}, ${shippingDetails.postalCode}\n" +
                shippingDetails.deliveryOption.displayText,
            style = AppTypography.bodyLarge,
            modifier = Modifier.padding(top = 5.dp, start = 25.dp),
            fontWeight = FontWeight.W900,
            fontSize = 17.sp
        )

        Spacer(
            modifier = Modifier
                .padding(top = 15.dp)
                .height(2.dp)
                .fillMaxWidth()
                .background(color = Color.LightGray)
        )

        OrderCartList(
            activity,
            foodViewModel,
            navController,
            modifier = Modifier.height(150.dp),
            totalAmount = totalAmount,
            isCheckoutScreen = true
        )

        Spacer(
            modifier = Modifier
                .padding(top = 40.dp)
                .height(2.dp)
                .fillMaxWidth()
                .background(color = Color.LightGray)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 30.dp, top = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Amount",
                fontSize = 18.sp,
                fontWeight = FontWeight.W600
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${totalAmount.value} lei",
                modifier = Modifier.padding(end = 30.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.W600
            )
        }

        GradientButton(
            modifier = Modifier
                .padding(top = 25.dp)
                .align(Alignment.CenterHorizontally),
            textButton = "Place Order",
            onClick = {}
        )
    }
}

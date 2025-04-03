package com.example.ecommercefoodappcompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommercefoodappcompose.ui.components.TextFieldWithValidation
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.theme.inversePrimaryDark

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.weight(0.25f))
        Text(
            text = "Food Store",
            fontSize = 50.sp,
            style = AppTypography.titleLarge,
            color = inversePrimaryDark
        )
        Spacer(modifier = Modifier.weight(0.15f))
        TextFieldWithValidation("Username", KeyboardType.Email, Icons.Filled.Person)
        TextFieldWithValidation(
            "Password",
            KeyboardType.Password,
            Icons.Filled.Lock,
            PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.weight(0.05f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 62.dp),
            onClick = { navController.navigate("home_screen") }
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}

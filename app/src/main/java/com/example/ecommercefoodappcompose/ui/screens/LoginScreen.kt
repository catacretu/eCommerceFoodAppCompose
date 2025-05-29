package com.example.ecommercefoodappcompose.ui.screens

import android.widget.Toast
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommercefoodappcompose.ui.components.TextFieldWithValidation
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.viewmodel.AuthUiState
import com.example.ecommercefoodappcompose.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val authUiState = authViewModel.authUiState

    LaunchedEffect(key1 = authUiState) {
        when (authUiState) {
            is AuthUiState.Success -> {
                navController.navigate("home_screen") {
                    popUpTo("login_screen") { inclusive = true }
                }
                authViewModel.resetAuthUiState()
            }
            is AuthUiState.Error -> {
                Toast.makeText(context, "Error: ${authUiState.message}", Toast.LENGTH_LONG).show()
                authViewModel.resetAuthUiState()
            }
            else -> Unit
        }
    }

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
            color = MaterialTheme.colorScheme.inversePrimary
        )
        Spacer(modifier = Modifier.weight(0.15f))
        TextFieldWithValidation(
            "Username",
            KeyboardType.Email,
            Icons.Filled.Person,
            value = authViewModel.email,
            onValueChange = {
                authViewModel.onEmailChange(it)
            }
        )
        TextFieldWithValidation(
            "Password",
            KeyboardType.Password,
            Icons.Filled.Lock,
            PasswordVisualTransformation(),
            value = authViewModel.password,
            onValueChange = {
                authViewModel.onPasswordChange(it)
            }
        )
        Spacer(modifier = Modifier.weight(0.05f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 62.dp),
            onClick = { authViewModel.login() },
            enabled = authUiState !is AuthUiState.Loading

        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}

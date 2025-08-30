package com.example.ecommercefoodappcompose.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommercefoodappcompose.ui.components.LogoutDialog
import com.example.ecommercefoodappcompose.ui.components.ThemeSelectorWithGradientBorder
import com.example.ecommercefoodappcompose.ui.components.bottomBar.BottomNavigationBar
import com.example.ecommercefoodappcompose.ui.theme.AppTheme
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.viewmodel.AuthUiState
import com.example.ecommercefoodappcompose.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    currentTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit
) {
    val context = LocalContext.current
    val authUiState = authViewModel.authUiState
    var showLogoutDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = authUiState) {
        when (authUiState) {
            is AuthUiState.LogoutSuccess -> {
                Toast.makeText(context, "Logged out successfully!", Toast.LENGTH_SHORT).show()
                navController.navigate("login_screen") {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "More Screen",
                        style = AppTypography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary,
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
            Text(
                text = "About Us",
                style = AppTypography.titleLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.padding(top = 15.dp, start = 20.dp, bottom = 10.dp)
            )
            Spacer(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
            )
            Text(
                text = "Contact Us",
                style = AppTypography.titleLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.padding(top = 15.dp, start = 20.dp, bottom = 10.dp)
            )
            Spacer(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
            )
            Text(
                text = "Themes",
                style = AppTypography.titleLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.padding(top = 15.dp, start = 20.dp, bottom = 10.dp)
            )

            ThemeSelectorWithGradientBorder(
                currentAppTheme = currentTheme,
                onThemeSelected = onThemeSelected,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .fillMaxWidth(0.8f)
            )

            Spacer(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
            )
            Text(
                text = "Log Out",
                style = AppTypography.titleLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier
                    .clickable {
                        showLogoutDialog = true
                    }
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 20.dp, bottom = 10.dp)
            )
            LogoutDialog(
                showDialog = showLogoutDialog,
                onConfirm = {
                    showLogoutDialog = false
                    authViewModel.logout()
                },
                onDismiss = {
                    showLogoutDialog = false
                }
            )
        }
    }
}

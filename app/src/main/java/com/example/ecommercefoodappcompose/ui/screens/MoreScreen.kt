package com.example.ecommercefoodappcompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecommercefoodappcompose.ui.components.bottomBar.BottomNavigationBar
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.theme.inversePrimaryDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(navController: NavController) {
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
            Text(
                text = "About Us",
                style = AppTypography.titleLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = inversePrimaryDark,
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
                color = inversePrimaryDark,
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
                text = "Change Theme",
                style = AppTypography.titleLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = inversePrimaryDark,
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
                text = "Log Out",
                style = AppTypography.titleLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = inversePrimaryDark,
                modifier = Modifier.padding(top = 15.dp, start = 20.dp, bottom = 10.dp)
            )
        }
    }
}

package com.example.ecommercefoodappcompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommercefoodappcompose.ui.components.ContactItem

@Composable
fun ContactUsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contact Us",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.inversePrimary,
            modifier = Modifier.padding(top = 75.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Get in touch with us for more details",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ContactItem(
                icon = Icons.Default.Place,
                title = "ADDRESS",
                content = "Bicaz-Chei, Neamt",
                modifier = Modifier.padding(top = 30.dp)
            )
            ContactItem(
                icon = Icons.Default.Phone,
                title = "PHONE",
                content = "0233 255 872",
                modifier = Modifier.padding(top = 30.dp)
            )
            ContactItem(
                icon = Icons.Default.Email,
                title = "EMAIL",
                content = "catalincretu@gmail.com",
                modifier = Modifier.padding(top = 30.dp)
            )
        }
    }
}

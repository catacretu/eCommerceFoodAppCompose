package com.example.ecommercefoodappcompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommercefoodappcompose.ui.viewmodel.PaymentViewModel

@Composable
fun PaymentScreen(paymentViewModel: PaymentViewModel = hiltViewModel()) {
    val uiState by paymentViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        uiState.errorMessage?.let { error ->
            Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        uiState.clientSecret?.let { clientSecret ->
            Text(text = "Client Secret obținut! Poți continua cu plata.")
            Text(text = clientSecret, style = MaterialTheme.typography.bodySmall)
            // Aici vei integra Stripe Payment Element sau similar, folosind clientSecret
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                // Suma în cenți (ex: 1000 pentru 10.00 USD)
                // Valuta în cod ISO (ex: "usd", "eur")
                paymentViewModel.initiatePayment(amount = 500, currency = "usd")
            },
            enabled = !uiState.isLoading
        ) {
            Text("Plătește 1.00 USD")
        }
    }
}

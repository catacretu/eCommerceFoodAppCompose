package com.example.ecommercefoodappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.ecommercefoodappcompose.ui.components.NavigationGraph
import com.example.ecommercefoodappcompose.ui.theme.AppTheme
import com.example.ecommercefoodappcompose.ui.theme.ECommerceFoodAppComposeTheme
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel
import com.example.ecommercefoodappcompose.ui.viewmodel.PaymentViewModel
import com.example.ecommercefoodappcompose.ui.viewmodel.RecipeViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val paymentViewModel: PaymentViewModel by viewModels()
    private lateinit var paymentSheet: PaymentSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PaymentConfiguration.init(
            applicationContext,
            PB_KEY
        )
        paymentSheet = PaymentSheet(this, paymentViewModel::onPaymentSheetResult)
        lifecycleScope.launch {
            paymentViewModel.paymentSheetConfig.collectLatest { config ->
                config?.let { (clientSecret, paymentSheetStripeConfig) ->
                    paymentSheet.presentWithPaymentIntent(clientSecret, paymentSheetStripeConfig)
                    paymentViewModel.paymentSheetPresentationDone()
                }
            }
        }
        enableEdgeToEdge()
        setContent {
            var currentTheme by rememberSaveable { mutableStateOf(AppTheme.GREEN) }
            val foodViewModel: FoodViewModel = hiltViewModel()
            val recipeViewModel: RecipeViewModel = hiltViewModel()
            ECommerceFoodAppComposeTheme(selectedTheme = currentTheme) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationGraph(
                        modifier = Modifier.padding(innerPadding),
                        activity = this@MainActivity,
                        context = this,
                        foodViewModel = foodViewModel,
                        recipeViewModel = recipeViewModel,
                        currentAppTheme = currentTheme,
                        onThemeChange = { newTheme ->
                            currentTheme = newTheme
                        }
                    )
                }
            }
        }
    }
}

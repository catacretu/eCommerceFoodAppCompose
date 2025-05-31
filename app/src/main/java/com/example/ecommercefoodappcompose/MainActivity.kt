package com.example.ecommercefoodappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommercefoodappcompose.ui.components.NavigationGraph
import com.example.ecommercefoodappcompose.ui.theme.AppTheme
import com.example.ecommercefoodappcompose.ui.theme.ECommerceFoodAppComposeTheme
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel
import com.example.ecommercefoodappcompose.ui.viewmodel.RecipeViewModel
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PaymentConfiguration.init(
            applicationContext,
            "pk_live_51MS0ndJj2PSkO5fyabieXVA4qRqptjkiImc8qqyCNf3jZOhE7ptxzwQ3vg5WXebwjo3uVgGF5CBEImRq4DHBYF0d003lOkl5AK"
        )
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

// @Preview(showBackground = true)
// @Composable
// fun GreetingPreview() {
//    ECommerceFoodAppComposeTheme {
//        NavigationGraph(Modifier)
//    }
// }

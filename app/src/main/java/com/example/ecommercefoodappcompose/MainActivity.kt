package com.example.ecommercefoodappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommercefoodappcompose.ui.components.NavigationGraph
import com.example.ecommercefoodappcompose.ui.theme.ECommerceFoodAppComposeTheme
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel
import com.example.ecommercefoodappcompose.ui.viewmodel.SuggestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val foodViewModel: FoodViewModel = hiltViewModel()
            val suggestionViewModel: SuggestionViewModel = hiltViewModel()
            ECommerceFoodAppComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationGraph(
                        modifier = Modifier.padding(innerPadding),
                        activity = this@MainActivity,
                        context = this,
                        foodViewModel = foodViewModel,
                        suggestionViewModel = suggestionViewModel
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

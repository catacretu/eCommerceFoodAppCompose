package com.example.ecommercefoodappcompose.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.data.repository.RecipeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepositoryImpl
) : ViewModel() {
    val recipeItems: LiveData<List<FoodItem>> = recipeRepository.allFoodItems
    val isLoading: LiveData<Boolean> = recipeRepository.isLoading
    var searchQuery by mutableStateOf("")

    fun searchRecipe(query: String) {
        viewModelScope.launch {
            recipeRepository.fetchRecipes(query)
        }
    }
}

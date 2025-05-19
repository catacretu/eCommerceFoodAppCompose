package com.example.ecommercefoodappcompose.data.repository

import androidx.lifecycle.LiveData
import com.example.ecommercefoodappcompose.data.local.model.FoodItem

interface SuggestionRepository {
    val allFoodItems: LiveData<List<FoodItem>>
    suspend fun fetchRecipe(query: String)
}

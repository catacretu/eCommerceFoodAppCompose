package com.example.ecommercefoodappcompose.data.repository

import androidx.lifecycle.LiveData
import com.example.ecommercefoodappcompose.data.local.dao.FoodDAO
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.data.remote.SuggestionService
import javax.inject.Inject

class SuggestionRepositoryImpl @Inject constructor(
    private val suggestionService: SuggestionService,
    private val foodDAO: FoodDAO
) : SuggestionRepository {

    override val allFoodItems: LiveData<List<FoodItem>> = foodDAO.getAllFoodItems()
    override suspend fun fetchRecipe(query: String) {}
}

package com.example.ecommercefoodappcompose.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommercefoodappcompose.data.local.dao.FoodDAO
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.data.remote.SuggestionService
import javax.inject.Inject

class SuggestionRepositoryImpl @Inject constructor(
    private val suggestionService: SuggestionService,
    private val foodDAO: FoodDAO
) : SuggestionRepository {

    val allFoodItems: LiveData<List<FoodItem>> = foodDAO.getAllFoodItems()
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    override suspend fun fetchRecipe(query: String) {}
}

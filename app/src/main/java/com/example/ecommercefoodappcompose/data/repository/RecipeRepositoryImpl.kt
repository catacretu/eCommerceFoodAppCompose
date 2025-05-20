package com.example.ecommercefoodappcompose.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommercefoodappcompose.data.local.dao.FoodDAO
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.data.remote.RecipeService
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeService: RecipeService,
    private val foodDAO: FoodDAO
) : RecipeRepository {

    val allFoodItems: LiveData<List<FoodItem>> = foodDAO.getAllFoodItems()
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    override suspend fun fetchRecipes(query: String) {}
}

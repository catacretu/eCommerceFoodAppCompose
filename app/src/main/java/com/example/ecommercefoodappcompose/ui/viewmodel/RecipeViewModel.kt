package com.example.ecommercefoodappcompose.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.data.local.model.RecipeItem
import com.example.ecommercefoodappcompose.data.repository.RecipeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepositoryImpl
) : ViewModel() {
    val isLoading: LiveData<Boolean> = recipeRepository.isLoading
    var searchQuery by mutableStateOf("")
    var shouldClearResults by mutableStateOf(false)

    private val _ingredientsList = MutableLiveData<List<FoodItem>>()
    val ingredientsList: LiveData<List<FoodItem>> = _ingredientsList

    val recipeItems: LiveData<List<RecipeItem>> = recipeRepository.recipes
    val foodItems: LiveData<List<FoodItem>> = recipeRepository.getAllFoodItems()
    val favouriteRecipes: LiveData<List<RecipeItem>> = recipeRepository.getFavouriteRecipes()
    val defaultRecipes: LiveData<List<RecipeItem>> = recipeRepository.getDefaultRecipes()
    private val _selectedRecipe = MutableLiveData<RecipeItem>()
    val selectedRecipe: LiveData<RecipeItem> = _selectedRecipe
    val isFavourite = recipeRepository.isFavourite

    fun searchRecipes(query: String, additionalSearch: Boolean = false) {
        viewModelScope.launch {
            recipeRepository.fetchRecipes(query, additionalSearch)
        }
        shouldClearResults = true
    }

    fun selectRecipe(recipe: RecipeItem) {
        updateIngredientsList(recipe.matchedIngredients)
        _selectedRecipe.value = recipe
    }

    fun toggleFavorite(recipe: RecipeItem) {
        viewModelScope.launch {
            recipeRepository.toggleFavorite(recipe)
        }
    }

    fun loadFavouriteStatus(id: Int) {
        viewModelScope.launch {
            isFavourite.value = recipeRepository.isRecipeFavourite(id)
        }
    }

    fun clearAllRecipes() {
        recipeRepository.clearAllRecipes()
        shouldClearResults = false
    }

    private fun updateIngredientsList(recipeIngredients: List<String>) {
        if (recipeIngredients.isNotEmpty()) {
            viewModelScope.launch {
                val ingredients = recipeRepository.getFoodItemsByNames(recipeIngredients)
                val allAreOthers = ingredients.isNotEmpty() && ingredients.all {
                    it.category.equals("Others", ignoreCase = true)
                }
                if (allAreOthers) {
                    _ingredientsList.postValue(emptyList())
                } else {
                    _ingredientsList.postValue(ingredients)
                }
            }
        } else {
            _ingredientsList.postValue(emptyList())
        }
    }

//    fun findMatchingIngredients(recipeIngredients: List<String>, foodItems: List<FoodItem>) {
//        val matchedIngredients = mutableListOf<FoodItem>()
// //        val allFoodItems = foodItems.value ?: emptyList()
//
//        val normalizedFoodItems = foodItems.map { foodItem ->
//            normalize(foodItem.name) to foodItem
//        }
//
//        for (ingredientLine in recipeIngredients) {
//            val normalizedLine = normalize(ingredientLine)
//
//            for ((normalizedName, foodItem) in normalizedFoodItems) {
//                if (normalizedLine.contains(normalizedName)) {
//                    matchedIngredients.add(foodItem)
//                }
//            }
//        }
//        _recipeIngredients.postValue(matchedIngredients.distinctBy { it.id })
//    }
//
//    private fun normalize(text: String): String {
//        return text.lowercase()
//            .replace("[^a-zăîâșț ]".toRegex(), "")
//            .replace("\\s+".toRegex(), " ")
//            .trim()
//    }
}

package com.example.ecommercefoodappcompose.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    val recipeItems: LiveData<List<RecipeItem>> = recipeRepository.recipes
    val favouriteRecipes: LiveData<List<RecipeItem>> = recipeRepository.getAllRecipes()
    private val _selectedRecipe = MutableLiveData<RecipeItem>()
    val selectedRecipe: LiveData<RecipeItem> = _selectedRecipe

    init {
        getAllFavouriteRecipes()
    }

    fun searchRecipes(query: String, additionalSearch: Boolean = false) {
        viewModelScope.launch {
            recipeRepository.fetchRecipes(query, additionalSearch)
        }
        shouldClearResults = true
    }

    fun selectRecipe(recipe: RecipeItem) {
        _selectedRecipe.value = recipe
    }

    private fun getAllFavouriteRecipes(): LiveData<List<RecipeItem>> {
        return recipeRepository.getAllRecipes()
    }

    fun toggleFavorite(recipe: RecipeItem) {
        viewModelScope.launch {
            recipeRepository.toggleFavorite(recipe)
        }
    }

    fun clearAllRecipes() {
        recipeRepository.clearAllRecipes()
        shouldClearResults = false
    }
}

package com.example.ecommercefoodappcompose.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.data.repository.SuggestionRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuggestionViewModel @Inject constructor(
    private val suggestionRepository: SuggestionRepositoryImpl
) : ViewModel() {
    val suggestionItems: LiveData<List<FoodItem>> = suggestionRepository.allFoodItems
    val isLoading: LiveData<Boolean> = suggestionRepository.isLoading
    var searchQuery by mutableStateOf("")

    fun searchRecipe(query: String) {
        viewModelScope.launch {
            suggestionRepository.fetchRecipe(query)
        }
    }
}

package com.example.ecommercefoodappcompose.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.data.repository.SuggestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SuggestionViewModel @Inject constructor(
    private val suggestionRepository: SuggestionRepository
) : ViewModel() {
    val suggestionItems: LiveData<List<FoodItem>> = suggestionRepository.allFoodItems
}

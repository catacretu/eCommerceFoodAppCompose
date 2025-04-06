package com.example.ecommercefoodappcompose.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.data.repository.FoodRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    foodRepository: FoodRepositoryImpl
) : ViewModel() {
    val foodItems: LiveData<List<FoodItem>> = foodRepository.getAllFoodItems()
    val isLoading: LiveData<Boolean> = foodRepository.isLoading
    init {
        // The data is already being emitted via LiveData, no explicit action is required here.
    }
}

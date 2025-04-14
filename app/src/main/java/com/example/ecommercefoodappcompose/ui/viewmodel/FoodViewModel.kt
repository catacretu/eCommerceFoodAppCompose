package com.example.ecommercefoodappcompose.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.data.repository.FoodRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val foodRepository: FoodRepositoryImpl
) : ViewModel() {
    val foodItems: LiveData<List<FoodItem>> = foodRepository.getAllFoodItems()
    val isLoading: LiveData<Boolean> = foodRepository.isLoading
    private val _selectedFoodItem = MutableLiveData<FoodItem>()
    val selectedFoodItem: LiveData<FoodItem> = _selectedFoodItem
    private val _cartItems = MutableLiveData<List<FoodItem>>()
    val cartItems: LiveData<List<FoodItem>> = _cartItems

    init {
        // The data is already being emitted via LiveData, no explicit action is required here.
    }
    fun selectFoodItem(foodItem: FoodItem) {
        _selectedFoodItem.value = foodItem
    }

    private suspend fun getFoodItemById(foodItemId: Int): FoodItem {
        return withContext(Dispatchers.IO) {
            foodRepository.getFoodItemById(foodItemId)
        }
    }

    fun loadCartItems() {
        viewModelScope.launch {
            val foodItemList = mutableListOf<FoodItem>()
            val item1 = getFoodItemById(1)
            val item2 = getFoodItemById(2)
            foodItemList.add(item1)
            foodItemList.add(item2)
            _cartItems.value = foodItemList
        }
    }
}

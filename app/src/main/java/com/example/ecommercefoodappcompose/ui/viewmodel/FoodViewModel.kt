package com.example.ecommercefoodappcompose.ui.viewmodel

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.data.local.model.ShippingDetailsItem
import com.example.ecommercefoodappcompose.data.local.model.SortOption
import com.example.ecommercefoodappcompose.data.repository.FoodRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val foodRepository: FoodRepositoryImpl
) : ViewModel() {
    val isLoading: LiveData<Boolean> = foodRepository.isLoading
    private val _isSearching = MutableLiveData<Boolean>()
    val isSearching: LiveData<Boolean> = _isSearching
    private val _selectedFoodItem = MutableLiveData<FoodItem>()
    val selectedFoodItem: LiveData<FoodItem> = _selectedFoodItem

    var activeFilters by mutableStateOf<Set<String>>(emptySet())
        private set
    var shippingDetailsState by mutableStateOf(ShippingDetailsItem())
        private set
    var searchQuery by mutableStateOf("")
        private set
    var sortOption by mutableStateOf(SortOption.NONE)
        private set
    private var searchJob: Job? = null

    val foodItems: LiveData<List<FoodItem>> = foodRepository.getAllFoodItems()
    private val _filteredFoodItems = MediatorLiveData<List<FoodItem>>()
    val filteredFoodItems: LiveData<List<FoodItem>> = _filteredFoodItems
    private val _cartItems = MutableLiveData<List<FoodItem>>()
    val cartItems: LiveData<List<FoodItem>> = _cartItems
    private val _favouritesItems = MutableLiveData<List<FoodItem>>()
    val favouritesItems: LiveData<List<FoodItem>> = _favouritesItems

    init {
        // The data is already being emitted via LiveData, no explicit action is required here.
        _filteredFoodItems.addSource(foodItems) {
            applyFilters()
        }
    }
    fun selectFoodItem(foodItem: FoodItem) {
        _selectedFoodItem.value = foodItem
    }

    private suspend fun getFoodItemById(foodItemId: Int): FoodItem {
        return withContext(Dispatchers.IO) {
            foodRepository.getFoodItemById(foodItemId)
        }
    }

    fun loadCartItems(activity: Activity) {
        val sh = activity.getSharedPreferences("shopping_cart", Context.MODE_PRIVATE)
        val shCartList = sh.all.keys
        val foodItemList = mutableListOf<FoodItem>()
        viewModelScope.launch {
            for (foodItemId in shCartList) {
                val itemId = foodItemId.toInt()
                val item = getFoodItemById(itemId)
                foodItemList.add(item)
            }
            _cartItems.value = foodItemList
        }
    }

    fun loadFavouritesItems(activity: Activity) {
        val sh = activity.getSharedPreferences("favourite", Context.MODE_PRIVATE)
        val shFavouriteList = sh.all.keys
        val foodItemList = mutableListOf<FoodItem>()
        viewModelScope.launch {
            for (foodItemId in shFavouriteList) {
                val itemId = foodItemId.toInt()
                val item = getFoodItemById(itemId)
                foodItemList.add(item)
            }
            _favouritesItems.value = foodItemList
        }
    }

    fun toggleCategoryFilter(category: String) {
        activeFilters = if (category in activeFilters) {
            activeFilters - category
        } else {
            activeFilters + category
        }
        applyFilters()
    }

    fun applyFilters() {
        _isSearching.value = true
        val allItems = foodItems.value ?: emptyList()
        viewModelScope.launch {
            delay(if (searchQuery.isEmpty()) 0 else 500)
            var filtered = allItems.filter { item ->
                val matchesCategory = activeFilters.isEmpty() || item.category in activeFilters
                val matchesSearch =
                    searchQuery.isBlank() || item.name.contains(searchQuery, ignoreCase = true)
                matchesCategory && matchesSearch
            }

            filtered = when (sortOption) {
                SortOption.NAME_ASC -> filtered.sortedBy { it.name }
                SortOption.NAME_DESC -> filtered.sortedByDescending { it.name }
                SortOption.PRICE_ASC -> filtered.sortedBy { extractPrice(it.price) }
                SortOption.PRICE_DESC -> filtered.sortedByDescending { extractPrice(it.price) }
                SortOption.NONE -> filtered
            }

            _filteredFoodItems.value = filtered
            _isSearching.value = false
        }
    }

    private fun extractPrice(priceString: String): Int {
        return Regex("lei|leu")
            .split(priceString)[0]
            .trim().trim().toInt()
    }

    fun addCartItem(foodItem: FoodItem) {
        val currentList = _cartItems.value?.toMutableList() ?: mutableListOf()

        if (currentList.none { it.id == foodItem.id }) {
            currentList.add(foodItem)
            _cartItems.value = currentList
        }
    }

    fun removeCartItem(foodItemId: Int) {
        val currentList = _cartItems.value?.toMutableList() ?: mutableListOf()
        currentList.removeAll { it.id == foodItemId }
        _cartItems.value = currentList
    }

    fun toggleFavourite(foodItem: FoodItem, isFavourite: Boolean) {
        val currentList = _favouritesItems.value?.toMutableList() ?: mutableListOf()
        if (isFavourite) {
            currentList.add(foodItem)
            _favouritesItems.value = currentList
        } else {
            currentList.remove(foodItem)
            _favouritesItems.value = currentList
        }
    }

    fun updateSearchQuery(newQuery: String) {
        searchQuery = newQuery
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            applyFilters()
        }
    }

    fun updateShippingDetails(newDetails: ShippingDetailsItem) {
        shippingDetailsState = newDetails
    }

    fun clearShippingDetails() {
        shippingDetailsState = ShippingDetailsItem()
    }

    fun updateSortOption(newSortOption: SortOption) {
        sortOption = newSortOption
        applyFilters()
    }
}

package com.example.ecommercefoodappcompose.data.repository

import androidx.lifecycle.LiveData
import com.example.ecommercefoodappcompose.data.local.model.FoodItem

interface FoodRepository {
    fun getAllFoodItems(): LiveData<List<FoodItem>>
}

package com.example.ecommercefoodappcompose.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommercefoodappcompose.data.local.model.FoodItem

@Dao
interface FoodDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFoodList(foodItemList: List<FoodItem>)

    @Query("Select * FROM food_table")
    fun getAllFoodItems(): LiveData<List<FoodItem>>

    @Query("Delete FROM food_table")
    suspend fun deleteAllFoodItems()
}

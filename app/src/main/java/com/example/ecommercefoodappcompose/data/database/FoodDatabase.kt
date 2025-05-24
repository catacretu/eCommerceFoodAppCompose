package com.example.ecommercefoodappcompose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ecommercefoodappcompose.data.local.dao.FoodDAO
import com.example.ecommercefoodappcompose.data.local.dao.RecipeDAO
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.data.local.model.RecipeItem
import com.example.ecommercefoodappcompose.data.local.model.TypeConverter

@Database(entities = [FoodItem::class, RecipeItem::class], version = 7, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDAO
    abstract fun recipeDao(): RecipeDAO
}

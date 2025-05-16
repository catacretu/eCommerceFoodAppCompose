package com.example.ecommercefoodappcompose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecommercefoodappcompose.data.local.dao.FoodDAO
import com.example.ecommercefoodappcompose.data.local.model.FoodItem

@Database(entities = [FoodItem::class], version = 5, exportSchema = false)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDAO
}

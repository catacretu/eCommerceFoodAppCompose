package com.example.ecommercefoodappcompose.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
class FoodItem(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val price: String,
    val description: String,
    val imageUrl: String,
    val rating: Float,
    var quantity: Int = 0
)

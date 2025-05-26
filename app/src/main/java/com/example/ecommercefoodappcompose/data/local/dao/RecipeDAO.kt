package com.example.ecommercefoodappcompose.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommercefoodappcompose.data.local.model.RecipeItem

@Dao
interface RecipeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeItem: RecipeItem)

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): LiveData<List<RecipeItem>>

    @Query("SELECT * FROM recipes WHERE isFavourite = 1")
    fun getDefaultRecipes(): LiveData<List<RecipeItem>>

    @Query("DELETE FROM recipes WHERE id = :recipeId")
    suspend fun deleteRecipeById(recipeId: Int)

    @Query("SELECT isFavourite FROM recipes WHERE id = :recipeId LIMIT 1")
    suspend fun isRecipeFavourite(recipeId: Int): Boolean
}

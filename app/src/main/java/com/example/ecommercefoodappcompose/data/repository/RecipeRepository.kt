package com.example.ecommercefoodappcompose.data.repository

interface RecipeRepository {
    suspend fun fetchRecipes(query: String, additionalSearch: Boolean)
}

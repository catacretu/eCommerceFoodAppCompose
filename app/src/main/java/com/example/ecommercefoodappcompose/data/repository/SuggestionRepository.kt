package com.example.ecommercefoodappcompose.data.repository

interface SuggestionRepository {
    suspend fun fetchRecipe(query: String)
}

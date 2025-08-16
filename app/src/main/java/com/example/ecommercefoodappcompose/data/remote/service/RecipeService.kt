package com.example.ecommercefoodappcompose.data.remote.service

import com.example.ecommercefoodappcompose.OPEN_API_KEY
import com.example.ecommercefoodappcompose.data.remote.entity.ChatRequest
import com.example.ecommercefoodappcompose.data.remote.entity.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RecipeService {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer $OPEN_API_KEY"
    )
    @POST("v1/chat/completions")
    suspend fun getRecipes(@Body request: ChatRequest): ChatResponse
}

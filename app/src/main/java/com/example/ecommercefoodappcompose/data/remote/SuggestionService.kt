package com.example.ecommercefoodappcompose.data.remote

import com.example.ecommercefoodappcompose.OPEN_API_KEY
import com.example.ecommercefoodappcompose.data.remote.entity.ChatRequest
import com.example.ecommercefoodappcompose.data.remote.entity.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SuggestionService {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer $OPEN_API_KEY"
    )
    @POST("v1/chat/completions")
    suspend fun getSuggestions(@Body request: ChatRequest): ChatResponse
}

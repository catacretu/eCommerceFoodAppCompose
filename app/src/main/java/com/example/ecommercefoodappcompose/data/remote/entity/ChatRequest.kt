package com.example.ecommercefoodappcompose.data.remote.entity

data class ChatRequest(
    val model: String,
    val messages: List<Message>,
    val temperature: Double
)

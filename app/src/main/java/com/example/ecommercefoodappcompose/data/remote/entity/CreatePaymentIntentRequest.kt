package com.example.ecommercefoodappcompose.data.remote.entity

data class CreatePaymentIntentRequest(
    val amount: Int,
    val currency: String
)

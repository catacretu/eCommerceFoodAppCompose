package com.example.ecommercefoodappcompose.data.repository

import com.example.ecommercefoodappcompose.data.remote.entity.PaymentIntentResponse

interface PaymentRepository {
    suspend fun createPaymentIntent(amount: Int, currency: String): PaymentIntentResponse
}

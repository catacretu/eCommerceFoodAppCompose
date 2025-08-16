package com.example.ecommercefoodappcompose.data.remote.service

import com.example.ecommercefoodappcompose.data.remote.entity.CreatePaymentIntentRequest
import com.example.ecommercefoodappcompose.data.remote.entity.PaymentIntentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface StripeApiService {
    @POST("/create-payment-intent")
    suspend fun createPaymentIntent(
        @Body requestBody: CreatePaymentIntentRequest
    ): Response<PaymentIntentResponse>
}

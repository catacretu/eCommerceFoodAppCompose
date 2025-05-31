package com.example.ecommercefoodappcompose.data.repository

import com.example.ecommercefoodappcompose.data.remote.StripeApiService
import com.example.ecommercefoodappcompose.data.remote.entity.CreatePaymentIntentRequest
import com.example.ecommercefoodappcompose.data.remote.entity.PaymentIntentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val stripeApiService: StripeApiService
): PaymentRepository {
    override suspend fun createPaymentIntent(amount: Int, currency: String): PaymentIntentResponse {
        return withContext(Dispatchers.IO) {
            try {
                val apiRequest = CreatePaymentIntentRequest(amount, currency)
                val response = stripeApiService.createPaymentIntent(apiRequest)

                if (response.isSuccessful) {
                    val serverResponse = response.body()
                    if (serverResponse?.clientSecret != null) {
                        PaymentIntentResponse(clientSecret = serverResponse.clientSecret, error = null)
                    } else {
                        // Răspuns de succes dar body gol sau clientSecret lipsă
                        PaymentIntentResponse(clientSecret = null, error = serverResponse?.error ?: "Missing clientSecret in response")
                    }
                } else {
                    // Eroare HTTP (4xx, 5xx)
                    // Încearcă să parsezi corpul erorii dacă serverul trimite un JSON și acolo
                    val errorBodyString = response.errorBody()?.string()
                    val errorMessage = if (errorBodyString != null) {
                        try {
                            // Presupunând că serverul trimite { "error": "mesaj" } și în caz de eroare HTTP
                            val errorJson = JSONObject(errorBodyString)
                            errorJson.optString("error", "HTTP Error: ${response.code()}")
                        } catch (e: Exception) {
                            "HTTP Error: ${response.code()} - ${response.message()}"
                        }
                    } else {
                        "HTTP Error: ${response.code()} - ${response.message()}"
                    }
                    PaymentIntentResponse(clientSecret = null, error = errorMessage)
                }
            } catch (e: Exception) { // Alte erori (de rețea, de parsare etc.)
                e.printStackTrace()
                PaymentIntentResponse(clientSecret = null, error = "Network request failed: ${e.message}")
            }
        }
    }
}

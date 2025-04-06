package com.example.ecommercefoodappcompose.data.remote

import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import retrofit2.Response
import retrofit2.http.GET

interface FoodService {
    @GET("/catacretu/eCommerceData/main/FoodData")
    suspend fun getAllFoodItems(): Response<List<FoodItem>>
}

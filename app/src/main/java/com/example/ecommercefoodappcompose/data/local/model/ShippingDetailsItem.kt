package com.example.ecommercefoodappcompose.data.local.model

data class ShippingDetailsItem(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = "",
    val postalCode: String = "",
    val deliveryOption: DeliveryOption = DeliveryOption.HOME_DELIVERY
)

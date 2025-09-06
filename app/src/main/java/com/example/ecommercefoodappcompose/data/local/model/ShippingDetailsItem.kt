package com.example.ecommercefoodappcompose.data.local.model

data class ShippingDetailsItem(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val county: String = "",
    val city: String = "",
    val street: String = "",
    val streetNumber: String = "",
    val unitDetails: String = "",
    val postalCode: String = "",
    val deliveryOption: DeliveryOption = DeliveryOption.HOME_DELIVERY
)

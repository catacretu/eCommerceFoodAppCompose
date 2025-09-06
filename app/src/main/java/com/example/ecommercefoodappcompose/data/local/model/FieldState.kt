package com.example.ecommercefoodappcompose.data.local.model

import androidx.compose.runtime.mutableStateOf

class FieldState(
    initial: String = "",
    private val validator: (String) -> String?
) {
    val state = mutableStateOf(initial)
    val error = mutableStateOf<String?>(null)
    val touched = mutableStateOf(false)

    fun onValueChange(newValue: String) {
        state.value = newValue
        touched.value = true
        error.value = validator(newValue)
    }
    fun isValid(): Boolean = error.value == null && state.value.isNotEmpty()
}

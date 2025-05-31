package com.example.ecommercefoodappcompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommercefoodappcompose.data.repository.PaymentRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PaymentUiState(
    val clientSecret: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepositoryImpl
) : ViewModel() {
    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState

    fun initiatePayment(amount: Int, currency: String) {
        viewModelScope.launch {
            _uiState.value = PaymentUiState(isLoading = true)
            val response = paymentRepository.createPaymentIntent(amount, currency)
            if (response.clientSecret != null) {
                _uiState.value = PaymentUiState(
                    clientSecret = response.clientSecret,
                    isLoading = false
                )
                println("Received clientSecret: ${response.clientSecret}")
            } else {
                _uiState.value = PaymentUiState(
                    errorMessage = response.error,
                    isLoading = false
                )
                println("Error: ${response.error}")
            }
        }
    }
}

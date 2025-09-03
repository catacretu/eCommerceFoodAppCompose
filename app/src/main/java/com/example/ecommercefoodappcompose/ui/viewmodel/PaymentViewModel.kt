package com.example.ecommercefoodappcompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommercefoodappcompose.data.repository.PaymentRepositoryImpl
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PaymentSheetPresentationConfig(
    val clientSecret: String,
    val configuration: PaymentSheet.Configuration
)

data class PaymentUiState(
    val clientSecret: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val paymentSheetResult: PaymentSheetResult? = null
)

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepositoryImpl
) : ViewModel() {
    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    private val _paymentSheetConfig = MutableStateFlow<PaymentSheetPresentationConfig?>(null)
    val paymentSheetConfig: StateFlow<PaymentSheetPresentationConfig?> =
        _paymentSheetConfig.asStateFlow()

    fun initiatePayment(amount: Int, currency: String, merchantDisplayName: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    paymentSheetResult = null,
                    errorMessage = null
                )
            }
            _paymentSheetConfig.value = null

            val response = paymentRepository.createPaymentIntent(amount, currency)
            if (response.clientSecret != null) {
                val clientSecret = response.clientSecret

                val stripeConfiguration = PaymentSheet.Configuration(
                    merchantDisplayName = merchantDisplayName,
                    // Optional: CustomerConfiguration pentru clienți existenți Stripe
                    // customer = PaymentSheet.CustomerConfiguration(
                    //     id = "cus_YOUR_CUSTOMER_ID",
                    //     ephemeralKeySecret = "ek_test_YOUR_EPHEMERAL_KEY_SECRET"
                    // ),
                    googlePay = PaymentSheet.GooglePayConfiguration(
                        environment = PaymentSheet.GooglePayConfiguration.Environment.Test,
                        countryCode = "RO"
                        // currencyCode = currency
                    ),
                    allowsDelayedPaymentMethods = true
                )

                _paymentSheetConfig.update {
                    PaymentSheetPresentationConfig(
                        clientSecret,
                        stripeConfiguration
                    )
                }
                _uiState.update { it.copy(isLoading = false) }
            } else {
                _uiState.update {
                    it.copy(errorMessage = response.error, isLoading = false)
                }
                println("Error fetching clientSecret: ${response.error}")
            }
        }
    }

    fun onPaymentSheetResult(paymentResult: PaymentSheetResult) {
        _uiState.update {
            it.copy(
                paymentSheetResult = paymentResult,
                isLoading = false
            )
        }
        _paymentSheetConfig.value = null
    }

    fun paymentSheetPresentationDone() {
        // Poate fi folosit pentru a reseta _paymentSheetConfig dacă este necesar
        // sau pentru a evita re-randarea la reconfigurări, deși collectLatest ar trebui să ajute.
        // Pentru moment, _paymentSheetConfig.value = null în onPaymentSheetResult și la începutul initiatePayment e suficient.
    }
}

package com.example.ecommercefoodappcompose.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthUiState {
    data object Idle : AuthUiState()
    data object Loading : AuthUiState()
    data object Success : AuthUiState()
    data object LogoutSuccess : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var authUiState by mutableStateOf<AuthUiState>(AuthUiState.Idle)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun login() {
        authUiState = AuthUiState.Idle

        if (email.isBlank() || password.isBlank()) {
            authUiState = AuthUiState.Error("Email and password cannot be empty")
            return
        }

        authUiState = AuthUiState.Loading

        viewModelScope.launch {
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        authUiState = if (task.isSuccessful) {
                            AuthUiState.Success
                        } else {
                            AuthUiState.Error(task.exception?.message ?: "Unknown error")
                        }
                    }
            } catch (e: Exception) {
                authUiState = AuthUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun logout() {
        authUiState = AuthUiState.Loading
        try {
            firebaseAuth.signOut()
            authUiState = AuthUiState.LogoutSuccess
        } catch (e: Exception) {
            authUiState = AuthUiState.Error(e.message ?: "Eroare la logout.")
        }
    }

    fun resetAuthUiState() {
        authUiState = AuthUiState.Idle
    }
}

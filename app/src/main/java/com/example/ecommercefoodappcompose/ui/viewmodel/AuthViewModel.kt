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
    object RegisterSuccess : AuthUiState()
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
    var confirmPassword by mutableStateOf("")
        private set
    var authUiState by mutableStateOf<AuthUiState>(AuthUiState.Idle)
        private set
    var emailError by mutableStateOf<String?>(null)
        private set
    var passwordError by mutableStateOf<String?>(null)
        private set
    var confirmPasswordError by mutableStateOf<String?>(null)

    fun onEmailChange(newEmail: String) {
        email = newEmail
        emailError = null
        authUiState = AuthUiState.Idle
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        passwordError = null
        authUiState = AuthUiState.Idle
    }

    fun onConfirmPasswordChange(newPassword: String) {
        confirmPassword = newPassword
        confirmPasswordError = null
        authUiState = AuthUiState.Idle
    }

    fun register() {
        resetFieldErrors()
        authUiState = AuthUiState.Idle

        var hasError = false
        if (email.isBlank()) {
            emailError = "Email cannot be empty."
            hasError = true
        }
        if (password.isBlank()) {
            passwordError = "Password cannot be empty."
            hasError = true
        }
        if (confirmPassword.isBlank()) {
            confirmPasswordError = "Confirm password cannot be empty."
            hasError = true
        }

        if (password.length < 6) {
            passwordError = "Password must be at least 6 characters long."
            hasError = true
        }
        if (password != confirmPassword) {
            confirmPasswordError = "Passwords do not match."
            if (password.length >= 6) {
                passwordError = "Passwords do not match."
            }
            hasError = true
        }

        if (hasError) return

        authUiState = AuthUiState.Loading

        viewModelScope.launch {
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            authUiState = AuthUiState.RegisterSuccess
                        } else {
                            // Firebase specific error handling
                            val errorMessage = task.exception?.message
                            if (errorMessage != null) {
                                when {
                                    errorMessage.contains("email address is already in use")
                                    -> emailError = "Email already in use."
                                    errorMessage.contains("The email address is badly formatted")
                                    -> emailError = "Email is badly formatted."
                                    // Add other specific Firebase Auth errors if needed
                                    else -> authUiState = AuthUiState.Error(errorMessage)
                                }
                            } else {
                                authUiState = AuthUiState.Error("Registration failed.")
                            }
                        }
                    }
            } catch (e: Exception) {
                authUiState = AuthUiState.Error(e.message ?: "An unknown error occurred.")
            }
        }
    }

    fun login() {
        resetFieldErrors()
        authUiState = AuthUiState.Idle

        var hasError = false
        if (email.isBlank()) {
            emailError = "Email cannot be empty."
            hasError = true
        }
        if (password.isBlank()) {
            passwordError = "Password cannot be empty."
            hasError = true
        }

        if (hasError) return

        authUiState = AuthUiState.Loading

        viewModelScope.launch {
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        authUiState = if (task.isSuccessful) {
                            AuthUiState.Success
                        } else {
                            AuthUiState.Error(task.exception?.message ?: "Login failed.")
                        }
                    }
            } catch (e: Exception) {
                authUiState = AuthUiState.Error(e.message ?: "An unknown error occurred.")
            }
        }
    }

    fun logout() {
        authUiState = AuthUiState.Loading
        try {
            firebaseAuth.signOut()
            authUiState = AuthUiState.LogoutSuccess
        } catch (e: Exception) {
            authUiState = AuthUiState.Error(e.message ?: "Logout error")
        }
    }

    fun resetAuthUiState() {
        authUiState = AuthUiState.Idle
    }

    private fun resetFieldErrors() {
        emailError = null
        passwordError = null
        confirmPasswordError = null
    }
}

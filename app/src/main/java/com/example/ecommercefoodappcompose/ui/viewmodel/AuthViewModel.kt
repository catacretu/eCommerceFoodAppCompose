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

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onConfirmPasswordChange(newPassword: String) {
        confirmPassword = newPassword
    }

    fun register() {
        authUiState = AuthUiState.Idle
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            authUiState = AuthUiState.Error("All fields must be filled.")
            return
        }
        if (password.length < 6) {
            authUiState = AuthUiState.Error("Password must be at least 6 characters long.")
            return
        }

        if (password != confirmPassword) {
            authUiState = AuthUiState.Error("Passwords do not match.")
            return
        }

        authUiState = AuthUiState.Loading

        viewModelScope.launch {
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        authUiState = if (task.isSuccessful) {
                            AuthUiState.RegisterSuccess
                        } else {
                            AuthUiState.Error(task.exception?.message ?: "Registration failed.")
                        }
                    }
            } catch (e: Exception) {
                authUiState = AuthUiState.Error(e.message ?: "An unknown error occurred.")
            }
        }
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
                            AuthUiState.Error(task.exception?.message ?: "Unknown login error")
                        }
                    }
            } catch (e: Exception) {
                authUiState = AuthUiState.Error(e.message ?: "Unknown login error")
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
}

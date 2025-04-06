package com.example.ecommercefoodappcompose.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import kotlinx.coroutines.delay

@Composable
fun HandleLoadingState(
    isLoading: State<Boolean>,
    showLoading: MutableState<Boolean>,
    minLoadingTime: Long = 500L,
    loadingStartTime: MutableState<Long>
) {
    LaunchedEffect(isLoading.value) {
        if (isLoading.value) {
            loadingStartTime.value = System.currentTimeMillis()
            showLoading.value = true
        } else {
            val elapsedTime = System.currentTimeMillis() - loadingStartTime.value
            if (elapsedTime < minLoadingTime) {
                delay(minLoadingTime - elapsedTime)
            }
            showLoading.value = false
        }
    }
}

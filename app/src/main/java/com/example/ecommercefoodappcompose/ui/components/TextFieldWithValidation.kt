package com.example.ecommercefoodappcompose.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TextFieldWithValidation(
    label: String,
    inputType: KeyboardType,
    icon: ImageVector,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var text by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            isError = it.isEmpty()
        },
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "User Icon",
                tint = MaterialTheme.colorScheme.inversePrimary
            )
        },
        isError = isError,
        supportingText = {
            if (isError) Text("Field cannot be empty", color = Color.Red)
        },
        keyboardOptions = KeyboardOptions(keyboardType = inputType),
        visualTransformation = visualTransformation
    )
}

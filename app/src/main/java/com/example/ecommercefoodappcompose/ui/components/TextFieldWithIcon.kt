package com.example.ecommercefoodappcompose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction

@Composable
fun TextFieldWithIcon(
    fieldName: String,
    fieldValue: MutableState<String>,
    icon: ImageVector,
    iconDescription: String,
    modifier: Modifier,
    focusRequester: FocusRequester,
    imeAction: ImeAction = ImeAction.Next,
    onNext: (() -> Unit)? = null,
    onDone: (() -> Unit)? = null
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = fieldValue.value,
        onValueChange = { newText ->
            fieldValue.value = newText
        },
        label = { Text(fieldName) },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onNext = {
                onNext?.invoke()
            },
            onDone = {
                onDone?.invoke()
                focusManager.clearFocus()
            }
        )
    )
}

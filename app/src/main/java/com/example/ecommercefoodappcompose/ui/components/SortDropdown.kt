package com.example.ecommercefoodappcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.ecommercefoodappcompose.data.local.model.SortOption

@Composable
fun SortDropdownMenu(
    isExpanded: Boolean,
    onDismissRequest: () -> Unit,
    selectedOption: SortOption,
    onSortSelected: (SortOption) -> Unit
) {
    val sortOptions = listOf(
        "None" to SortOption.NONE,
        "Name A-Z" to SortOption.NAME_ASC,
        "Name Z-A" to SortOption.NAME_DESC,
        "Price ↑" to SortOption.PRICE_ASC,
        "Price ↓" to SortOption.PRICE_DESC
    )

    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismissRequest,
        offset = DpOffset(x = 0.dp, y = 15.dp)
    ) {
        sortOptions.forEach { (label, option) ->
            val isSelected = option == selectedOption
            DropdownMenuItem(
                text = { Text(label) },
                onClick = {
                    onSortSelected(option)
                    onDismissRequest()
                },
                modifier = if (isSelected) {
                    Modifier
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                } else {
                    Modifier
                },
                colors = MenuDefaults.itemColors(
                    textColor = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
            )
        }
    }
}

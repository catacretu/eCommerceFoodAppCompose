package com.example.ecommercefoodappcompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.ecommercefoodappcompose.data.local.model.DeliveryOption

@Composable
fun DeliveryOptionsGroup(
    selectedOption: DeliveryOption,
    onOptionSelected: (DeliveryOption) -> Unit,
    modifier: Modifier
) {
    val deliveryOptions = DeliveryOption.entries.toTypedArray()
    Column(modifier = modifier.selectableGroup()) {
//        Text(
//            "Delivery options:",
//            style = AppTypography.titleSmall,
//            modifier = Modifier.padding(bottom = 5.dp)
//        )
        deliveryOptions.forEach { deliveryOption ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (deliveryOption == selectedOption),
                        onClick = { onOptionSelected(deliveryOption) },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 5.dp),
                verticalAlignment = CenterVertically
            ) {
                RadioButton(
                    selected = (deliveryOption == selectedOption),
                    onClick = null // onClick is handle by parent row
                )
                Text(
                    text = deliveryOption.displayText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

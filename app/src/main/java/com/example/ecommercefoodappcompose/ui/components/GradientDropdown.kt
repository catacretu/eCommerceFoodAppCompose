package com.example.ecommercefoodappcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommercefoodappcompose.ui.theme.AppTheme
import com.example.ecommercefoodappcompose.ui.theme.primaryLightGreen
import com.example.ecommercefoodappcompose.ui.theme.primaryLightRed

@Composable
fun GradientBorderDropdown(
    selectedThemeName: String,
    themeColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val circleShape = RoundedCornerShape(50)
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            themeColor,
            themeColor
        )
    )
    Box(
        modifier = modifier
            .height(56.dp)
            .clip(circleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(2.dp, gradientBrush, circleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // circle
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(themeColor, CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = selectedThemeName,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontSize = 16.sp,
                    maxLines = 1
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Select a theme",
                tint = MaterialTheme.colorScheme.inversePrimary
            )
        }
    }
}

@Composable
fun ThemeSelectorWithGradientBorder(
    currentAppTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val baseColor = MaterialTheme.colorScheme.inversePrimary

    val representativeColor = remember(currentAppTheme) {
        baseColor
//        when (currentAppTheme) {
//            AppTheme.RED -> Color(0xFFB71C1C)
//            AppTheme.GREEN -> Color(0xFF2E7D32)
//        }
    }

    Box(modifier = modifier) {
        GradientBorderDropdown(
            selectedThemeName = currentAppTheme.name.replaceFirstChar
            { if (it.isLowerCase()) it.titlecase() else it.toString() } + " Theme",
            themeColor = representativeColor,
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh)
        ) {
            val baseRedColor = primaryLightRed
            val baseGreenColor = primaryLightGreen
            AppTheme.entries.forEach { theme ->
                val itemColor = when (theme) {
                    AppTheme.RED -> baseRedColor
                    AppTheme.GREEN -> baseGreenColor
                }
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                Modifier
                                    .size(16.dp)
                                    .background(itemColor, CircleShape)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                theme.name.replaceFirstChar
                                { if (it.isLowerCase()) it.titlecase() else it.toString() } +
                                    " Theme",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    onClick = {
                        onThemeSelected(theme)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A1E)
@Composable
fun PreviewGradientBorderDropdown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedThemeName by remember { mutableStateOf("Imperial Theme") }
    var themeColor by remember { mutableStateOf(Color(0xFF00E5FF)) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        GradientBorderDropdown(
            selectedThemeName = selectedThemeName,
            themeColor = themeColor,
            onClick = { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF1C1C3A))
        ) {
            DropdownMenuItem(
                text = { Text("Imperial Theme", color = Color.White) },
                onClick = {
                    selectedThemeName = "Imperial Theme"
                    themeColor = Color(0xFF00E5FF)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Rebel Red", color = Color.White) },
                onClick = {
                    selectedThemeName = "Rebel Red"
                    themeColor = Color(0xFFFF4136)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Jedi Green", color = Color.White) },
                onClick = {
                    selectedThemeName = "Jedi Green"
                    themeColor = Color(0xFF2ECC40)
                    expanded = false
                }
            )
        }
    }
}

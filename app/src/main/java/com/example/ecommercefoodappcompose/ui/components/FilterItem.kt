package com.example.ecommercefoodappcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommercefoodappcompose.ui.theme.inversePrimaryDark
import com.example.ecommercefoodappcompose.ui.theme.inversePrimaryDarkHighContrast

@Composable
fun FilterItem(
    icon: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) inversePrimaryDark else Color(0xFFF5F5F5)
    val iconTint = if (isSelected) Color.White else Color.DarkGray
    val textColor = if (isSelected) inversePrimaryDarkHighContrast else Color.DarkGray

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(backgroundColor)
                .clickable { onClick() }
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = label,
            color = textColor,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

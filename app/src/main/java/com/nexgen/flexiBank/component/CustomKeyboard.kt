package com.nexgen.flexiBank.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Define colors here to avoid dependency on specific theme files
private val BackgroundColor = Color(0xFFF5F5F5)
private val Blue = Color(0xFF0066FF)
private val Black = Color(0xFF000000)
private const val buttonSize = 90

@Composable
fun CustomKeyboard(
    onNumberClick: (String) -> Unit,
    onClearClick: () -> Unit,
    onDeleteClick: () -> Unit,
    isConfirmMode: Boolean = false
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (number in 1..3) {
                KeyboardButton(
                    text = number.toString(),
                    onClick = { onNumberClick(number.toString()) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Row 2: 4, 5, 6
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (number in 4..6) {
                KeyboardButton(
                    text = number.toString(),
                    onClick = { onNumberClick(number.toString()) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Row 3: 7, 8, 9
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (number in 7..9) {
                KeyboardButton(
                    text = number.toString(),
                    onClick = { onNumberClick(number.toString()) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Row 4: Clear, 0, Delete
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Clear button with blue styling
            Box(
                modifier = Modifier
                    .size(buttonSize.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE6F0FF))
                    .clickable(onClick = onClearClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Clear",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Blue
                )
            }

            KeyboardButton(
                text = "0",
                onClick = { onNumberClick("0") }
            )

            Box(
                modifier = Modifier
                    .size(buttonSize.dp)
                    .clip(CircleShape)
                    .background(BackgroundColor)
                    .clickable(onClick = onDeleteClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Delete",
                    tint = Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun KeyboardButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(buttonSize.dp)
            .clip(CircleShape)
            .background(BackgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = Black
        )
    }
}

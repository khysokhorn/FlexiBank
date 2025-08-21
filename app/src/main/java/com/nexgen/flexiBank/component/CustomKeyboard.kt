package com.nexgen.flexiBank.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
    isConfirmMode: Boolean = false,
    color: Color = BackgroundColor,
    imageDrawable: Int? = null,
    clearButtonText: String = "Clear",
    deleteButtonDrawable: Int? = null,
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
                    onClick = { onNumberClick(number.toString()) },
                    color = color
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (number in 4..6) {
                KeyboardButton(
                    text = number.toString(),
                    onClick = { onNumberClick(number.toString()) },
                    color = color
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (number in 7..9) {
                KeyboardButton(
                    text = number.toString(),
                    onClick = { onNumberClick(number.toString()) },
                    color = color
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Decimal point button
            Box(
                modifier = Modifier
                    .size(buttonSize.dp)
                    .clip(CircleShape)
                    .background(color)
                    .clickable(onClick = { onNumberClick(".") }),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = ".",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    color = Black
                )
            }

            // Zero button
            KeyboardButton(
                text = "0",
                onClick = { onNumberClick("0") },
                color = color
            )

            // Delete button
            Box(
                modifier = Modifier
                    .size(buttonSize.dp)
                    .clip(CircleShape)
                    .background(color)
                    .clickable(onClick = onDeleteClick),
                contentAlignment = Alignment.Center
            ) {
                val drawableToUse = deleteButtonDrawable ?: imageDrawable
                if (drawableToUse != null) {
                    Icon(
                        painter = painterResource(id = drawableToUse),
                        contentDescription = "Delete",
                        tint = Black,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Delete",
                        tint = Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}


@Composable
private fun KeyboardButton(text: String, onClick: () -> Unit, color: Color) {
    Box(
        modifier = Modifier
            .size(buttonSize.dp)
            .clip(CircleShape)
            .background(color)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Black,
        )
    }
}
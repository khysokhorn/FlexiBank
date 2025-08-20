package com.nexgen.flexiBank.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexgen.flexiBank.module.view.utils.text.InterNormal
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Hint
import kotlinx.coroutines.delay

@Composable
fun CurrencyTextField(
    amount: String,
    currencySymbol: String = "$",
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = currencySymbol,
            style = TextStyle(
                fontSize = 32.sp,
                fontFamily = InterNormal,
                fontWeight = FontWeight.SemiBold,
                color = Hint,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(contentAlignment = Alignment.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (amount.isEmpty()) {
                    Text(
                        text = "0",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontFamily = InterNormal,
                            fontWeight = FontWeight.SemiBold,
                            color = Hint,
                            textAlign = TextAlign.Center
                        )
                    )
                } else {
                    Text(
                        text = amount,
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontFamily = InterNormal,
                            fontWeight = FontWeight.SemiBold,
                            color = Black,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                BlinkingCursor()
            }
        }
    }
}

@Composable
private fun BlinkingCursor() {
    var visible by remember { mutableStateOf(true) }
    var animationProgress by remember { mutableStateOf(0f) }

    // Animate color transition
    LaunchedEffect(visible) {
        if (visible) {
            // Fade in from Hint to Black
            animationProgress = 0f
            while (animationProgress < 1f) {
                animationProgress += 0.05f
                delay(25) // Smooth color transition over 500ms
            }
            animationProgress = 1f
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            visible = !visible
        }
    }

    val cursorColor = androidx.compose.ui.graphics.lerp(Hint, Black, animationProgress)

    if (visible) {
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(32.dp)
                .background(cursorColor)
        )
    } else {
        Spacer(modifier = Modifier.width(1.5.dp))
    }
}
package com.nexgen.flexiBank.module.view.bakongQRCode.componnet

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ScannerFrame(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Scanner Animation")

    val animatedScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Corner Scale"
    )

    // Scanning line animation
    val scanLinePosition by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(offsetMillis = 500)
        ),
        label = "Scan Line"
    )

    // Opacity animation for the border
    val animatedAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Border Opacity"
    )
    Box(
        modifier = modifier
            .size(280.dp)
    ) {
        // Scanning line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0f),
                            Color.White.copy(alpha = 0.8f),
                            Color.White.copy(alpha = 0f)
                        )
                    )
                )
                .align(Alignment.Center)
                .offset(y = (140.dp * scanLinePosition))
        )
        // Top Left Corner
        ScannerCorner(
            modifier = Modifier
                .background(color = Color.Transparent)
                .align(Alignment.TopStart),
            rotationDegrees = 0f,
            progress = animatedScale
        )

        // Top Right Corner
        ScannerCorner(
            modifier = Modifier
                .background(color = Color.Transparent)
                .align(Alignment.TopEnd),
            rotationDegrees = 90f,
            progress = animatedScale
        )

        // Bottom Right Corner
        ScannerCorner(
            modifier = Modifier.align(Alignment.BottomEnd),
            rotationDegrees = 180f,
            progress = animatedScale
        )

        // Bottom Left Corner
        ScannerCorner(
            modifier = Modifier.align(Alignment.BottomStart),
            rotationDegrees = 270f,
            progress = animatedScale
        )
    }
}

@Composable
private fun ScannerCorner(
    modifier: Modifier = Modifier,
    rotationDegrees: Float,
    progress: Float,
    radius: Int = 16,
    lineLength: Int = 44
) {
    // Create the corner by using a single container with proper padding and shape
    Box(
        modifier = modifier
            .scale(progress)
            .rotate(rotationDegrees)
    ) {
        // Horizontal line with rounded end
        Box(
            modifier = Modifier
                .width(lineLength.dp)
                .height(3.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.White,
                            Color.White.copy(alpha = 0.7f)
                        )
                    ),
                    shape = RoundedCornerShape(topEnd = radius.dp, bottomEnd = radius.dp)
                )
        )

        // Vertical line with rounded end
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(lineLength.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color.White.copy(alpha = 0.7f)
                        )
                    ),
                    shape = RoundedCornerShape(bottomStart = radius.dp, bottomEnd = radius.dp)
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QRScannerPreview() {
    Box(
        modifier = Modifier
            .size(400.dp)
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .border(
                    width = 2.dp,
                    color = Color.White.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(1.dp)
        ) {
            // Mock camera preview background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
                    .border(
                        width = 3.dp,
                        color = Color.White.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(24.dp)
                    )
            )

            ScannerFrame(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
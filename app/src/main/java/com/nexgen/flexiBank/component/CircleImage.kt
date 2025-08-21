package com.nexgen.flexiBank.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import coil3.compose.SubcomposeAsyncImage

@Composable
fun CircleImage(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    imageUrl: String? = null,
    @DrawableRes placeholderResId: Int? = null,
    @DrawableRes errorResId: Int? = null,
    backgroundColor: Color = Color(0xFFF5F6FA)
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        when {
            // Case 1: Network image with URL
            !imageUrl.isNullOrEmpty() -> {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Circle Image",
                    modifier = Modifier.size(size),
                    contentScale = ContentScale.Crop,
                    loading = {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color(0xFFAEB5FF)
                        )
                    },
                    error = {
                        // Show error drawable if provided, otherwise show placeholder
                        if (errorResId != null) {
                            Image(
                                painter = painterResource(errorResId),
                                contentDescription = "Error Image",
                                modifier = Modifier.size(size),
                                contentScale = ContentScale.Crop
                            )
                        } else if (placeholderResId != null) {
                            Image(
                                painter = painterResource(placeholderResId),
                                contentDescription = "Placeholder Image",
                                modifier = Modifier.size(size),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                )
            }
            // Case 2: Local drawable resource
            placeholderResId != null -> {
                Image(
                    painter = painterResource(placeholderResId),
                    contentDescription = "Circle Image",
                    modifier = Modifier.size(size),
                    contentScale = ContentScale.Crop
                )
            }
            // Case 3: Fallback for no image (empty box with background)
        }
    }
}

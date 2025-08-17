package com.nexgen.flexiBank.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.nexgen.flexiBank.utils.theme.LightBlue

@Composable
fun CircleImage(
    backgroundColor: Color = Color(0x665260FE)
) {
    AsyncImage(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(Color.Transparent)
            .border(2.dp, LightBlue, CircleShape),
        model = "https://img.freepik.com/premium-psd/stylish-young-man-3d-icon-avatar-people_431668-1607.jpg",
        contentDescription = "Merchant",
    )
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    CircleImage()
}
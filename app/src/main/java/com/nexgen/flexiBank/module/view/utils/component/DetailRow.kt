package com.nexgen.flexiBank.module.view.utils.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nexgen.flexiBank.module.view.utils.text.InterNormal
import com.nexgen.flexiBank.module.view.utils.text.InterSemiBold
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Hint

@Composable
fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label, style = TextStyle(
                fontSize = 12.sp,
                fontFamily = InterNormal,
                fontWeight = FontWeight.W400,
                color = Hint,
            )
        )
        Text(
            text = value, style = TextStyle(
                fontSize = 12.sp,
                fontFamily = InterSemiBold,
                fontWeight = FontWeight.W500,
                color = Black,
            )
        )
    }
}
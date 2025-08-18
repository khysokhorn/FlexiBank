package com.nexgen.flexiBank.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun CurrencyTextField(
    amount: String,
    currencySymbol: String = "$",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Currency symbol
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
            Spacer(
                modifier = Modifier.width(4.dp)
            )
            Text(
                text = amount.ifEmpty { "0.00" },
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = InterNormal,
                    fontWeight = FontWeight.SemiBold,
                    color = Black,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

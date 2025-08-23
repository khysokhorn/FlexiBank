package com.nexgen.flexiBank.module.view.bakongQRCode.componnet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.module.view.utils.text.InterNormal
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Hint

@Composable
fun Remark(remark: String) {
    return Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = remark.ifEmpty { "Remark" },
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 18.sp,
                fontFamily = InterNormal,
                fontWeight = FontWeight(400),
                color = if (remark.isEmpty()) Hint else Black,
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(R.drawable.img_exit),
            contentDescription = "Icon Edit Remark",
            tint = Hint
        )
    }
}

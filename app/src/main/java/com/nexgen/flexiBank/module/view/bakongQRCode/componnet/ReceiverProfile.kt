package com.nexgen.flexiBank.module.view.bakongQRCode.componnet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.component.CircleImage
import com.nexgen.flexiBank.module.view.utils.text.InterNormal
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Hint

@Composable
fun ReceiverProfile() {
    return Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleImage()
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Row {
                Text(
                    text = "Thee Heeartless", style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = InterNormal,
                        fontWeight = FontWeight(600),
                        color = Black,
                        textAlign = TextAlign.Center,
                    )
                )
                Image(
                    modifier = Modifier
                        .width(64.dp)
                        .height(16.dp),
                    painter = painterResource(R.drawable.img_kh_qr),
                    contentDescription = "KHQR Image"
                )
            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "001 369 963 | Philip Bank",
                style = TextStyle(
                    fontSize = 11.sp,
                    lineHeight = 16.5.sp,
                    fontFamily = InterNormal,
                    fontWeight = FontWeight(400),
                    color = Hint,
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}
package com.nexgen.flexiBank.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexgen.flexiBank.module.view.utils.text.InterNormal
import com.nexgen.flexiBank.utils.theme.Primary
import com.nexgen.flexiBank.utils.theme.White

@Composable
fun PaymentSuccessScreen(
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Image(
//            painter = painterResource(R.drawable.ic_payment_success),
//            contentDescription = "Payment Success",
//            modifier = Modifier.size(120.dp)
//        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Payment Successful",
            style = TextStyle(
                fontSize = 22.sp,
                fontFamily = InterNormal,
                fontWeight = FontWeight.W600
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Your payment has been processed successfully",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = InterNormal,
                fontWeight = FontWeight.W400
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = onFinish,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary
            )
        ) {
            Text(
                text = "Done",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = InterNormal,
                    fontWeight = FontWeight.W600,
                    color = White
                )
            )
        }
    }
}

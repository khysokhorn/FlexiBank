package com.nexgen.flexiBank.module.view.bakongQRCode.componnet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import com.nexgen.flexiBank.module.view.bakongQRCode.model.Account
import com.nexgen.flexiBank.module.view.utils.text.InterNormal
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Hint

@Composable
fun SelectAccount(
    selectedAccount: Account
) {
    return Column {
        Text(
            text = "From:", style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 12.5.sp,
                fontFamily = InterNormal,
                fontWeight = FontWeight(600),
                color = Hint,
            )
        )
        Row(
            modifier = Modifier.padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${selectedAccount.number} | USD",
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    fontFamily = InterNormal,
                    fontWeight = FontWeight(400),
                    color = Black,
                )
            )
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = 4.dp),
                painter = painterResource(R.drawable.img_arrow_down),
                contentDescription = "Icon Select account",
            )
        }
    }
}
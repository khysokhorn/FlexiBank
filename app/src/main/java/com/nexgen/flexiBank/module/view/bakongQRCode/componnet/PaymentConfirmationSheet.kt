package com.nexgen.flexiBank.module.view.bakongQRCode.componnet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.component.CircleImage
import com.nexgen.flexiBank.module.view.bakongQRCode.model.Account
import com.nexgen.flexiBank.module.view.utils.text.InterNormal
import com.nexgen.flexiBank.module.view.utils.text.InterSemiBold
import com.nexgen.flexiBank.utils.theme.BackgroundColor
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Hint
import com.nexgen.flexiBank.utils.theme.Primary
import com.nexgen.flexiBank.utils.theme.Warning
import com.nexgen.flexiBank.utils.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentConfirmationSheet(
    show: Boolean,
    amount: String,
    fromAccount: Account,
    toName: String,
    toAccountNumber: String,
    toBank: String,
    remark: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (show) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            containerColor = White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            dragHandle = {
                BottomSheetDefaults.DragHandle(
                    color = Hint
                )
            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 34.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Transaction Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircleImage(
                                size = 46.dp,
                                backgroundColor = BackgroundColor,
                                placeholderResId = R.drawable.img_merchant
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "$amount USD", style = TextStyle(
                                            fontSize = 14.sp,
                                            fontFamily = InterNormal,
                                            fontWeight = FontWeight.W600,
                                            color = Black,
                                        )
                                    )
                                }
                                Text(
                                    text = "Transferred to $toName", style = TextStyle(
                                        fontSize = 11.sp,
                                        fontFamily = InterNormal,
                                        fontWeight = FontWeight.W400,
                                        color = Hint,
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Image(
                                painter = painterResource(R.drawable.img_kh_qr),
                                contentDescription = "KHQR Logo",
                                modifier = Modifier
                                    .width(46.dp)
                                    .height(20.dp)
                            )
                        }
                    }
                }

                // Transaction Details
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DetailRow(
                        label = "From account:",
                        value = "${fromAccount.name} (${fromAccount.number})"
                    )
                    DetailRow(
                        label = "To bank:", value = toBank
                    )
                    DetailRow(
                        label = "To account:", value = "$toName ($toAccountNumber)"
                    )
                    DetailRow(
                        label = "Fee:", value = "Free"
                    )
                }

                if (remark.isNotEmpty())
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    BackgroundColor.copy(alpha = 0.5f), RoundedCornerShape(16.dp)
                                )
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Remark",
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontFamily = InterNormal,
                                    fontWeight = FontWeight.W400,
                                    color = Hint,
                                )
                            )
                            Text(
                                text = remark,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = InterNormal,
                                    fontWeight = FontWeight.W400,
                                    color = Black,
                                )
                            )
                        }
                    }

                // Warning Message
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(500.dp))
                        .background(Warning.copy(alpha = 0.1f))
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        tint = Warning,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Confirm transfer details before proceeding.", style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = InterNormal,
                            fontWeight = FontWeight.W400,
                            color = Black
                        ), modifier = Modifier.weight(1f)
                    )
                }
                // Confirm Button
                ConfirmButton(
                    onConfirm = {
                    }
                )
            }
        }
    }
}

@Composable
private fun ConfirmButton(
    onConfirm: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var isProcessing by remember { mutableStateOf(false) }
    Button(
        onClick = {
            if (!isProcessing) {
                isProcessing = true
                // Simulate API call
                coroutineScope.launch {
                    delay(1000)

                    val apiResponseCode = -1

                    if (apiResponseCode == -1) {
                        onConfirm()
                    } else {
                        onConfirm()
                    }

                    isProcessing = false
                }
            }
        },
        enabled = !isProcessing,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary
        )
    ) {
        Text(
            text = if (isProcessing) "Processing..." else "Confirm", style = TextStyle(
                fontSize = 16.sp,
                fontFamily = InterNormal,
                fontWeight = FontWeight.W600,
                color = White
            )
        )
    }
}

@Composable
private fun DetailRow(
    label: String, value: String
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
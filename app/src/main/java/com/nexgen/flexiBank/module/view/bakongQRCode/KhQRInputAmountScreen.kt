package com.nexgen.flexiBank.module.view.bakongQRCode

import androidx.activity.compose.LocalActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.component.CircleImage
import com.nexgen.flexiBank.component.CurrencyTextField
import com.nexgen.flexiBank.component.CustomKeyboard
import com.nexgen.flexiBank.module.view.bakongQRCode.componnet.AccountSelectionBottomSheet
import com.nexgen.flexiBank.module.view.bakongQRCode.componnet.PaymentConfirmationSheet
import com.nexgen.flexiBank.module.view.bakongQRCode.componnet.RemarkDialog
import com.nexgen.flexiBank.module.view.bakongQRCode.model.Account
import com.nexgen.flexiBank.module.view.bakongQRCode.viewModel.KhQrInputAmountViewModel
import com.nexgen.flexiBank.module.view.keypass.VerifyPinFragment
import com.nexgen.flexiBank.module.view.utils.text.InterNormal
import com.nexgen.flexiBank.utils.theme.BackgroundColor
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Hint
import com.nexgen.flexiBank.utils.theme.Primary
import com.nexgen.flexiBank.utils.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KhQRInputAmountScreen(
    viewModel: KhQrInputAmountViewModel,
    onNavigateBack: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var showRemarkDialog by remember { mutableStateOf(false) }
    var showAccountSheet by remember { mutableStateOf(false) }
    var selectedAccount by remember { mutableStateOf(viewModel.sampleAccounts[0]) }
    var remark by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.paymentSuccess.collectAsState().value) {
        if (viewModel.paymentSuccess.value) {
            onPaymentSuccess()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TopAppBar(
                title = { Text(text = "Scan QR") }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            painter = painterResource(R.drawable.img_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                ReceiverProfile()

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CurrencyTextField(
                        amount = amount,
                        currencySymbol = "USD"
                    )
                }

                AccountAndRemarkSection(
                    selectedAccount = selectedAccount,
                    remark = remark,
                    onAccountClick = { showAccountSheet = true },
                    onRemarkClick = { showRemarkDialog = true })

                CustomKeyboard(
                    onNumberClick = { digit ->
                        if (digit == "." && amount.contains(".")) return@CustomKeyboard
                        if (amount.contains(".")) {
                            val decimalPlaces = amount.substringAfter(".").length
                            if (decimalPlaces >= 2) return@CustomKeyboard
                        }
                        amount += digit
                        viewModel.addDigit(digit)
                    },
                    onClearClick = {
                        amount = ""
                        viewModel.clearPin()
                    },
                    onDeleteClick = {
                        if (amount.isNotEmpty()) {
                            amount = amount.dropLast(1)
                        }
                        viewModel.deleteLastDigit()
                    },
                    color = Color.Transparent,
                    deleteButtonDrawable = R.drawable.icon_backspace,
                )

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = { showConfirmation = true },
                    enabled = amount.isNotEmpty() && (amount.toDoubleOrNull() ?: 0.0) > 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFAEB5FF),
                        disabledContainerColor = Color(0xFFAEB5FF).copy(alpha = 0.5f)
                    )
                ) {
                    Text(
                        text = "Send",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }

        // Loading Indicator
        if (viewModel.isLoading.collectAsState().value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Primary)
            }
        }

        // Error Snackbar
        viewModel.error.collectAsState().value?.let { errorMessage ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Snackbar(
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(
                        text = errorMessage, color = White
                    )
                }
            }
        }
    }

    // Bottom Sheets and Dialogs
    AccountSelectionBottomSheet(
        show = showAccountSheet,
        accounts = viewModel.sampleAccounts,
        selectedAccountId = selectedAccount.id,
        onAccountSelected = { account ->
            selectedAccount = account
            showAccountSheet = false
        },
        onDismiss = { showAccountSheet = false })

    RemarkDialog(
        showDialog = showRemarkDialog,
        initialValue = remark,
        onDismiss = { showRemarkDialog = false },
        onSave = { newRemark ->
            remark = newRemark
            showRemarkDialog = false
        })

    PaymentConfirmationSheet(
        show = showConfirmation,
        amount = amount,
        fromAccount = selectedAccount,
        toName = "Thee Heeartless",
        toAccountNumber = "001 369 963",
        toBank = "Phillip Bank Plc.",
        remark = remark,
        onConfirm = {
            showConfirmation = false
            viewModel.submitPayment(
                amount = amount,
                accountId = selectedAccount.id,
                remark = remark
            )
        },
        onDismiss = { showConfirmation = false }
    )

    // Handle verification requirement
    val requireVerification by viewModel.requireVerification.collectAsState()
    if (requireVerification) {
        val activity = LocalActivity.current as? AppCompatActivity
        activity?.let {
            val verifyPinFragment = VerifyPinFragment.newInstance(
                isStandalone = false,
//                isFromConfirmation = true,
//                onVerificationSuccess = {
//                    viewModel.continueAfterVerification()
//                }
            )
            // Use post to avoid transaction during view layout
            it.window.decorView.post {
                activity.supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, verifyPinFragment)
                    .addToBackStack(null)
                    .commit()
            }
            // Reset the requirement flag
            viewModel.resetVerification()
        }
    }
}

@Composable
private fun AccountAndRemarkSection(
    selectedAccount: Account, remark: String, onAccountClick: () -> Unit, onRemarkClick: () -> Unit
) {
    Row(
        modifier = Modifier.background(White, RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .weight(1F)
                .height(48.dp)
                .background(
                    BackgroundColor, RoundedCornerShape(
                        topStart = 16.dp, bottomStart = 16.dp
                    )
                )
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp, bottomStart = 16.dp
                    )
                )
                .clickable(onClick = onAccountClick)
                .padding(8.dp)
        ) {
            Column {
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
                        text = "${selectedAccount.number} | USD", style = TextStyle(
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
        Box(
            modifier = Modifier
                .width(2.dp)
                .background(White)
        )
        Box(
            modifier = Modifier
                .weight(1F)
                .height(48.dp)
                .clickable(onClick = onRemarkClick)
                .background(
                    BackgroundColor, RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                )
                .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = remark.ifEmpty { "Remark" }, style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        fontFamily = InterNormal,
                        fontWeight = FontWeight(400),
                        color = if (remark.isEmpty()) Hint else Black,
                    )
                )
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 4.dp),
                    painter = painterResource(R.drawable.img_exit),
                    contentDescription = "Icon Edit Remark",
                    tint = Hint
                )
            }
        }
    }
}

@Composable
private fun ReceiverProfile() {
    Row(
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
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
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
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                )
            )
        }
    }
}

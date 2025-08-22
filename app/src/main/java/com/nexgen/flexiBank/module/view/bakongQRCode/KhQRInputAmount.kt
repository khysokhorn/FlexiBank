package com.nexgen.flexiBank.module.view.bakongQRCode

import android.os.Bundle
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.findNavController
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.component.CircleImage
import com.nexgen.flexiBank.component.CurrencyTextField
import com.nexgen.flexiBank.component.CustomKeyboard
import com.nexgen.flexiBank.module.view.bakongQRCode.componnet.AccountSelectionBottomSheet
import com.nexgen.flexiBank.module.view.bakongQRCode.componnet.PaymentConfirmationSheet
import com.nexgen.flexiBank.module.view.bakongQRCode.componnet.RemarkDialog
import com.nexgen.flexiBank.module.view.bakongQRCode.model.Account
import com.nexgen.flexiBank.module.view.bakongQRCode.viewModel.KhQrInputAmountViewModel
import com.nexgen.flexiBank.module.view.base.BaseComposeActivity
import com.nexgen.flexiBank.module.view.utils.text.InterNormal
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.utils.theme.BackgroundColor
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Hint
import com.nexgen.flexiBank.utils.theme.Primary
import com.nexgen.flexiBank.utils.theme.White


class KhQRInputAmountActivity : BaseComposeActivity<KhQrInputAmountViewModel, AppRepository>() {
    private var pendingPaymentData: PaymentData? = null
    data class PaymentData(
        val amount: String,
        val accountId: String,
        val remark: String
    )

    @Composable
    override fun ComposeContent() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Body()

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
                            text = errorMessage,
                            color = White
                        )
                    }
                }
            }

            LaunchedEffect(viewModel.paymentSuccess.collectAsState().value) {
                if (viewModel.paymentSuccess.value) {
                    finish()
                }
            }
        }
    }

    private val sampleAccounts = listOf(
        Account(
            id = "1",
            name = "Saving Account",
            number = "001 751 517",
            balance = "$ 72,392.10",
            isDefault = true,
            hasVisa = true
        ),
        Account(
            id = "2",
            name = "Future Plan",
            number = "001 222 333",
            balance = "$ 2,392.68",
            isDefault = false,
            hasVisa = false,
            iconRes = R.drawable.ic_bank_locker
        )
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Body() {
        var amount by remember { mutableStateOf("") }
        var showRemarkDialog by remember { mutableStateOf(false) }
        var showAccountSheet by remember { mutableStateOf(false) }
        var selectedAccount by remember { mutableStateOf(sampleAccounts[0]) }
        var remark by remember { mutableStateOf("") }
        var showConfirmation by remember { mutableStateOf(false) }
        Column {
            Box {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Scan QR", style = TextStyle(
                                color = Black,
                                fontSize = 22.sp,
                                fontFamily = InterNormal,
                                fontWeight = FontWeight.W600,
                            )
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            ReceiverProfile()
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CurrencyTextField(
                                    amount = amount, currencySymbol = "USD"
                                )
                            }
                            Row(
                                modifier = Modifier.background(White, RoundedCornerShape(16.dp))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1F)
                                        .height(48.dp)
                                        .background(
                                            BackgroundColor,
                                            RoundedCornerShape(
                                                topStart = 16.dp,
                                                bottomStart = 16.dp
                                            )
                                        )
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 16.dp,
                                                bottomStart = 16.dp
                                            )
                                        )
                                        .clickable { showAccountSheet = true }
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
                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .background(White)
                                )
                                Box(
                                    modifier = Modifier
                                        .weight(1F)
                                        .height(48.dp)
                                        .clickable(onClick = { showRemarkDialog = true })
                                        .background(
                                            BackgroundColor,
                                            RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
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
                                            text = remark.ifEmpty { "Remark" },
                                            style = TextStyle(
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
                        Box(modifier = Modifier.padding(vertical = 16.dp)) {
                            CustomKeyboard(
                                onNumberClick = { digit ->
                                    // Only allow one decimal point
                                    if (digit == "." && amount.contains(".")) {
                                        return@CustomKeyboard
                                    }
                                    // Don't allow more than 2 decimal places
                                    if (amount.contains(".")) {
                                        val decimalPlaces = amount.substringAfter(".").length
                                        if (decimalPlaces >= 2) {
                                            return@CustomKeyboard
                                        }
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
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        Button(
                            onClick = { showConfirmation = true },
                            enabled = amount.isNotEmpty() && (amount.toDoubleOrNull() ?: 0.0) > 0,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp),
                            shape = RoundedCornerShape(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Primary,
                                disabledContainerColor = Primary.copy(alpha = 0.5f)
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
            }
        }

        AccountSelectionBottomSheet(
            show = showAccountSheet,
            accounts = sampleAccounts,
            selectedAccountId = selectedAccount.id,
            onAccountSelected = { account ->
                selectedAccount = account
                showAccountSheet = false
            },
            onDismiss = { showAccountSheet = false }
        )
        RemarkDialog(
            showDialog = showRemarkDialog,
            initialValue = remark,
            onDismiss = { showRemarkDialog = false },
            onSave = { newRemark ->
                remark = newRemark
                showRemarkDialog = false
            }
        )
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
                // Store payment data for potential retry after PIN verification
                pendingPaymentData = PaymentData(
                    amount = amount,
                    accountId = selectedAccount.id,
                    remark = remark
                )
                submitPaymentWithPinVerification(amount, selectedAccount.id, remark)
            },
            onDismiss = { showConfirmation = false }
        )
    }

    private fun submitPaymentWithPinVerification(
        amount: String,
        accountId: String,
        remark: String
    ) {
        handleApiResponseWithPinRequired(amount, accountId, remark)
    }

    private fun handleApiResponseWithPinRequired(
        amount: String,
        accountId: String,
        remark: String
    ) {
        showPinVerificationScreen()
    }

    private fun showPinVerificationScreen() {

        try {
            val navController = findNavController(R.id.nav_host_fragment)
            val bundle = Bundle().apply {
                putBoolean("isStandalone", false) // Not standalone, part of payment flow
            }
            navController.navigate(R.id.verifyPinFragment, bundle)
        } catch (e: Exception) {
            // Fallback if navigation fails
            // In a real app, you would handle this more gracefully
        }
    }
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CustomToolBar() {
        return TopAppBar(
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.img_arrow_back),
                        contentDescription = "Back Button",
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                    )
                }
            }, title = { }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent, titleContentColor = Color.White
            )
        )
    }

    override fun getViewModel(): Class<KhQrInputAmountViewModel> =
        KhQrInputAmountViewModel::class.java

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(this, ApiInterface::class.java))

    @Preview(showBackground = true)
    @Composable
    override fun ContentPreview() {
        ComposeContent()
    }
}
package com.nexgen.flexiBank.module.view.bakongQRCode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.component.CircleImage
import com.nexgen.flexiBank.component.CustomKeyboard
import com.nexgen.flexiBank.module.view.bakongQRCode.model.AccountModel
import com.nexgen.flexiBank.module.view.bakongQRCode.viewModel.KhQrInputAmountViewModel
import com.nexgen.flexiBank.module.view.base.BaseComposeActivity
import com.nexgen.flexiBank.module.view.utils.text.InterNormal
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Hint


class KhQRInputAmountActivity : BaseComposeActivity<KhQrInputAmountViewModel, AppRepository>() {
    @Composable
    private fun AccountSelectionDialog() {
//        val isVisible by viewModel.isAccountDialogVisible
//        val accounts by viewModel.accountList.collectAsState()
//        val selectedAccount by viewModel.selectedAccount.collectAsState()
//
//        if (isVisible) {
//            Dialog(onDismissRequest = { viewModel.hideAccountDialog() }) {
//                Surface(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentHeight()
//                        .padding(horizontal = 16.dp),
//                    shape = RoundedCornerShape(16.dp),
//                    color = Color.White
//                ) {
//                    Column(
//                        modifier = Modifier.padding(16.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(bottom = 24.dp),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                text = "Select Account",
//                                style = TextStyle(
//                                    fontSize = 18.sp,
//                                    fontFamily = InterNormal,
//                                    fontWeight = FontWeight.W600,
//                                    color = Black
//                                )
//                            )
//                            IconButton(
//                                onClick = { viewModel.hideAccountDialog() },
//                                modifier = Modifier.size(24.dp)
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Default.Close,
//                                    contentDescription = "Close",
//                                    tint = Color(0xFF6C7278)
//                                )
//                            }
//                        }
//                        LazyColumn(
//                            verticalArrangement = Arrangement.spacedBy(16.dp),
//                            modifier = Modifier.weight(1f, false)
//                        ) {
//                            items(accounts) { account ->
//                                AccountItem(
//                                    account = account,
//                                    isSelected = account == selectedAccount,
//                                    onSelect = {
//                                        viewModel.selectAccount(account)
//                                        viewModel.hideAccountDialog()
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    @Composable
    private fun AccountItem(
        account: AccountModel,
        isSelected: Boolean,
        onSelect: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .clickable(onClick = onSelect)
                .background(
                    color = if (isSelected) Color(0xFFF8F9FF) else Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (isSelected) Color(0xFF3A4AFD) else Color(0xFFE8ECF0),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = account.accountName,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = InterNormal,
                            fontWeight = FontWeight.W600,
                            color = Black
                        )
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = account.accountNumber,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = InterNormal,
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF6C7278)
                            )
                        )
                        Text(
                            text = "•",
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color(0xFF6C7278)
                            )
                        )
                        Text(
                            text = "${account.currency} ${String.format("%,.2f", account.balance)}",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = InterNormal,
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF6C7278)
                            )
                        )
                    }
                }
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color(0xFF3A4AFD),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }

    @Composable
    private fun RemarkDialog() {
        val isVisible by viewModel.isRemarkDialogVisible
        var remarkText by remember { mutableStateOf("") }

        if (isVisible) {
            Dialog(onDismissRequest = { viewModel.hideRemarkDialog() }) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Add Remark",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = InterNormal,
                                fontWeight = FontWeight.W600,
                                color = Black
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        OutlinedTextField(
                            value = remarkText,
                            onValueChange = { remarkText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            placeholder = { 
                                Text(
                                    "Enter remark", 
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontFamily = InterNormal,
                                        fontWeight = FontWeight.W400,
                                        color = Hint
                                    )
                                )
                            },
                            maxLines = 1,
                            shape = RoundedCornerShape(8.dp),
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = InterNormal,
                                fontWeight = FontWeight.W400,
                                color = Black
                            )
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = { viewModel.hideRemarkDialog() },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text(
                                    "Cancel",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontFamily = InterNormal,
                                        fontWeight = FontWeight.W600,
                                        color = Color(0xFF3A4AFD)
                                    )
                                )
                            }
                            Button(
                                onClick = { 
                                    viewModel.setRemark(remarkText)
                                    viewModel.hideRemarkDialog()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF3A4AFD)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    "Save",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontFamily = InterNormal,
                                        fontWeight = FontWeight.W600,
                                        color = Color.White
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    override fun ComposeContent() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues())
        ) {
            Body()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Body() {
        var amount by remember { mutableStateOf("") }
        var remark by remember { mutableStateOf("") }
        var showAccountPicker by remember { mutableStateOf(false) }
        var showRemarkDialog by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box {
                Image(
                    painter = painterResource(R.drawable.img_bg_input_amount),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(390.dp),
                    contentScale = ContentScale.FillWidth,
                    alpha = 0.2f
                )
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(
                                    painter = painterResource(R.drawable.img_arrow_back),
                                    contentDescription = "Back Button",
                                    modifier = Modifier
                                        .width(28.dp)
                                        .height(28.dp)
                                )
                            }
                        },
                        title = { },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Scan QR",
                            style = TextStyle(
                                color = Black,
                                fontSize = 22.sp,
                                fontFamily = InterNormal,
                                fontWeight = FontWeight.W600,
                                lineHeight = 33.sp
                            )
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircleImage()
                            Column(
                                modifier = Modifier.padding(start = 16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Thee Heeartless",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontFamily = InterNormal,
                                            fontWeight = FontWeight.W600,
                                            color = Black
                                        )
                                    )
                                    Image(
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .width(28.dp)
                                            .height(12.dp),
                                        painter = painterResource(R.drawable.img_kh_qr),
                                        contentDescription = "KHQR Image"
                                    )
                                }
                                Text(
                                    text = "001 369 963 | Philip Bank",
                                    style = TextStyle(
                                        fontSize = 11.sp,
                                        lineHeight = 16.5.sp,
                                        fontFamily = InterNormal,
                                        fontWeight = FontWeight.W400,
                                        color = Hint
                                    ),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 25.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "$",
                                    style = TextStyle(
                                        fontSize = 31.sp,
                                        fontFamily = InterNormal,
                                        fontWeight = FontWeight.W600,
                                        color = Black
                                    )
                                )
                                Text(
                                    text = viewModel.amount.collectAsState().value.ifEmpty { "0" },
                                    style = TextStyle(
                                        fontSize = 40.sp,
                                        fontFamily = InterNormal,
                                        fontWeight = FontWeight.W600,
                                        color = Black
                                    )
                                )
                            }

                            // Show error if any
                            viewModel.error.collectAsState().value?.let { error ->
                                Text(
                                    text = error,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontFamily = InterNormal,
                                        fontWeight = FontWeight.W400,
                                        color = Color.Red
                                    ),
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 25.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(0.45f)
                                        .height(50.dp)
                                        .background(
                                            color = Color(0xFFF3F5F7),
                                            shape = RoundedCornerShape(
                                                topStart = 34.dp,
                                                bottomStart = 34.dp
                                            )
                                        )
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Column {
                                        Text(
                                            text = "From:",
                                            style = TextStyle(
                                                fontSize = 8.sp,
                                                fontFamily = InterNormal,
                                                fontWeight = FontWeight.W400,
                                                color = Hint
                                            )
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(top = 4.dp)
                                        ) {
                                            Text(
                                                text = "001 751 517 | USD",
                                                style = TextStyle(
                                                    fontSize = 12.sp,
                                                    fontFamily = InterNormal,
                                                    fontWeight = FontWeight.W400,
                                                    color = Black
                                                )
                                            )
                                            Icon(
                                                painter = painterResource(R.drawable.img_arrow_down),
                                                contentDescription = "Down Arrow",
                                                modifier = Modifier
                                                    .padding(start = 4.dp)
                                                    .size(16.dp)
                                            )
                                        }
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(0.55f)
                                        .height(50.dp)
                                        .background(
                                            color = Color(0xFFF3F5F7),
                                            shape = RoundedCornerShape(
                                                topEnd = 34.dp,
                                                bottomEnd = 34.dp
                                            )
                                        )
                                        .padding(horizontal = 12.dp)
                                        .clickable { showRemarkDialog = true },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = viewModel.remark.collectAsState().value,
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                fontFamily = InterNormal,
                                                fontWeight = FontWeight.W400,
                                                color = Black
                                            ),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.weight(1f)
                                        )
                                        IconButton(
                                            onClick = { viewModel.setRemark("") },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.img_eye_close),
                                                contentDescription = "Clear remark",
                                                tint = Hint
                                            )
                                        }
                                    }
                                }

                                if (showRemarkDialog) {
                                    RemarkDialog(
//                                        currentRemark = viewModel.remark.collectAsState().value,
//                                        onDismiss = { showRemarkDialog = false },
//                                        onSave = { newRemark ->
//                                            viewModel.setRemark(newRemark)
//                                            showRemarkDialog = false
//                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column {
                    CustomKeyboard(
                        onNumberClick = { digit ->
                            viewModel.addDigit(digit)
                        },
                        onClearClick = {
                            viewModel.clearAmount()
                        },
                        onDeleteClick = {
                            viewModel.deleteLastDigit()
                        },
                        isConfirmMode = true,
                        color = Color.Transparent
                    )
                    Button(
                        onClick = { viewModel.sendMoney() },
                        enabled = !viewModel.isLoading.collectAsState().value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 20.dp)
                            .height(58.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3A4AFD)
                        )
                    ) {
                        if (viewModel.isLoading.collectAsState().value) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Send",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = InterNormal,
                                    fontWeight = FontWeight.W600,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }
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
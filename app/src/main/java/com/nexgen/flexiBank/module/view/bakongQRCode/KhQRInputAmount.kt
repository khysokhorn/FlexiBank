package com.nexgen.flexiBank.module.view.bakongQRCode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.component.CircleImage
import com.nexgen.flexiBank.component.CurrencyTextField
import com.nexgen.flexiBank.component.CustomKeyboard
import com.nexgen.flexiBank.module.view.bakongQRCode.componnet.RemarkDialog
import com.nexgen.flexiBank.module.view.bakongQRCode.viewModel.KhQrInputAmountViewModel
import com.nexgen.flexiBank.module.view.base.BaseComposeActivity
import com.nexgen.flexiBank.module.view.utils.text.InterNormal
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.utils.theme.BackgroundColor
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Hint
import com.nexgen.flexiBank.utils.theme.White


class KhQRInputAmountActivity : BaseComposeActivity<KhQrInputAmountViewModel, AppRepository>() {
    @Composable
    override fun ComposeContent() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Body()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Body() {
        var amount by remember { mutableStateOf("") }
        var showRemarkDialog by remember { mutableStateOf(false) }
        var remark by remember { mutableStateOf("") }
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
                                                text = "001 751 517 | USD", style = TextStyle(
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
                                        .background(
                                            BackgroundColor,
                                            RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                                        )
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
                                            text = if (remark.isEmpty()) "Remark" else remark,
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                lineHeight = 18.sp,
                                                fontFamily = InterNormal,
                                                fontWeight = FontWeight(400),
                                                color = if (remark.isEmpty()) Hint else Black,
                                            )
                                        )
                                        IconButton(
                                            onClick = { showRemarkDialog = true }
                                        ) {
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
                        }
                        CustomKeyboard(
                            onNumberClick = { digit ->
                                amount += digit
                                viewModel.addDigit(digit)
                            }, onClearClick = {
                                amount = ""
                                viewModel.clearPin()
                            }, onDeleteClick = {
                                if (amount.isNotEmpty()) {
                                    amount = amount.dropLast(1)
                                }
                                viewModel.deleteLastDigit()
                            }, isConfirmMode = true, color = Color.Transparent
                        )
                        // Remark Dialog
                        RemarkDialog(
                            showDialog = showRemarkDialog,
                            initialValue = remark,
                            onDismiss = { showRemarkDialog = false },
                            onSave = { newRemark ->
                                remark = newRemark
                                showRemarkDialog = false
                            }
                        )
                    }
                }

            }
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
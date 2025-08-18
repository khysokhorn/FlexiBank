package com.nexgen.flexiBank.module.view.bakongQRCode

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
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
import com.nexgen.flexiBank.module.view.bakongQRCode.viewModel.KhQrInputAmountViewModel
import com.nexgen.flexiBank.module.view.base.BaseComposeActivity
import com.nexgen.flexiBank.module.view.utils.text.InterNormal
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Hint


class KhQRInputAmountActivity : BaseComposeActivity<KhQrInputAmountViewModel, AppRepository>() {
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
        val hapticFeedback = LocalHapticFeedback.current
        Column {
            Box {
                Image(
                    painter = painterResource(R.drawable.img_bg_input_amount),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                    contentScale = ContentScale.FillWidth,
                )
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TopAppBar(
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
                        },
                        title = { },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = Color.White
                        )
                    )
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            CurrencyTextField(
                                amount = amount,
                                currencySymbol = "USD",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        CustomKeyboard(
                            onNumberClick = { digit ->
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
                            isConfirmMode = true,
                            color = Color.Transparent
                        )
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
package com.nexgen.flexiBank.module.view.keypass

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.component.CustomKeyboard
import com.nexgen.flexiBank.databinding.ActivityKeypassBinding
import com.nexgen.flexiBank.module.view.base.BaseComposeActivity
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.repository.BaseRepository
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Blue
import com.nexgen.flexiBank.utils.theme.BorderColor
import com.nexgen.flexiBank.utils.theme.FlexiBankTheme
import com.nexgen.flexiBank.utils.theme.Gray600
import com.nexgen.flexiBank.utils.theme.White
import com.nexgen.flexiBank.viewmodel.PasscodeViewModel
import kotlinx.coroutines.launch

class KeypassActivity :
    BaseComposeActivity<PasscodeViewModel, ActivityKeypassBinding, AppRepository>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.isNavigateToNext.collect { shouldNavigate ->
                if (shouldNavigate) {
                    navigateToDashboard()
                    viewModel.resetNavigation()
                }
            }
        }
    }

    private fun navigateToDashboard() {

    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun initComposeContent() {
        setContent {
            FlexiBankTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { /* Empty title */ },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = White
                            ),
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        painter = painterResource(
                                            id = R.drawable.img_arrow_back,
                                        ),
                                        contentDescription = "Back",
                                        tint = Color.Black
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    PassCodeScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun getViewModel() = PasscodeViewModel::class.java

    override fun getActivityBinding(): ActivityKeypassBinding =
        ActivityKeypassBinding.inflate(layoutInflater)

    override fun getRepository() =
        AppRepository(
            remoteDataSource.buildApi(
                this,
                ApiInterface::class.java
            )
        )

    @Composable
    fun PassCodeScreen(
        viewModel: PasscodeViewModel,
        modifier: Modifier = Modifier
    ) {
        val pinCode by viewModel.pinCode.collectAsState(initial = "")
        val confirmPinCode by viewModel.confirmPinCode.collectAsState(initial = "")
        val isConfirmPin by viewModel.isConfirmPin.collectAsState(initial = false)
        val pinMatchError by viewModel.pinMatchError.collectAsState(initial = false)
        val maxPinLength = viewModel.getMaxPinLength()

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(White)
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 40.dp)
            ) {
                Text(
                    text = if (isConfirmPin) "Confirm Passcode" else "Create Passcode",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = when {
                        isConfirmPin && pinMatchError -> "PINs don't match. Try again."
                        isConfirmPin -> "Re-enter your passcode to confirm"
                        else -> "New passcode to secure your account"
                    },
                    fontSize = 16.sp,
                    color = if (pinMatchError) Color.Red else Gray600,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(maxPinLength) { index ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        isConfirmPin && index < confirmPinCode.length -> Blue
                                        !isConfirmPin && index < pinCode.length -> Blue
                                        else -> BorderColor
                                    }
                                )
                        )
                    }
                }

                if (isConfirmPin) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Forgot your PIN?",
                        fontSize = 14.sp,
                        color = Blue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { viewModel.resetToCreatePin() }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            CustomKeyboard(
                onNumberClick = { digit -> viewModel.addDigit(digit) },
                onClearClick = { viewModel.clearPin() },
                onDeleteClick = { viewModel.deleteLastDigit() },
                isConfirmMode = isConfirmPin
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun CreatePinScreenPreview() {
        // For preview, create a mock ViewModel for create PIN mode
        val mockViewModel = PasscodeViewModel(object : BaseRepository() {})
        FlexiBankTheme {
            PassCodeScreen(viewModel = mockViewModel)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ConfirmPinScreenPreview() {
        // For preview, create a mock ViewModel for confirm PIN mode
        val mockViewModel = PasscodeViewModel(object : BaseRepository() {})
        FlexiBankTheme {
            PassCodeScreen(viewModel = mockViewModel)
        }
    }
}
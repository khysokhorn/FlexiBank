package com.nexgen.flexiBank.module.view.pin

import android.os.Bundle
import android.view.View
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nexgen.flexiBank.component.CustomKeyboard
import com.nexgen.flexiBank.module.view.bakongQRCode.model.TodoModelItem
import com.nexgen.flexiBank.module.view.base.BaseComposeFragment
import com.nexgen.flexiBank.module.view.pin.viewModel.PinVerifyViewModel
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Blue
import com.nexgen.flexiBank.utils.theme.BorderColor
import com.nexgen.flexiBank.utils.theme.Gray600
import com.nexgen.flexiBank.utils.theme.White
import kotlinx.coroutines.launch

class PinVerifyFragment : BaseComposeFragment<PinVerifyViewModel, AppRepository>() {
    private var isStandaloneVerification = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setConfirmMode(false)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.verificationCompleted.collect { isCompleted ->
                if (isCompleted) {
                    handleVerificationSuccess()
                }
            }
        }
    }

    @Composable
    override fun ComposeContent() {
        VerifyPinScreen(viewModel = viewModel)
    }

    override fun getViewModel(): Class<PinVerifyViewModel> = PinVerifyViewModel::class.java

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(ApiInterface::class.java))

    @Composable
    fun VerifyPinScreen(
        viewModel: PinVerifyViewModel,
        modifier: Modifier = Modifier
    ) {
        val pinCode by viewModel.pinCode.collectAsState(initial = "")
        val maxPinLength = viewModel.getMaxPinLength()
        var showError by remember { mutableStateOf(false) }

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
                    text = "Enter Passcode",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Enter your passcode to continue",
                    fontSize = 16.sp,
                    color = if (showError) Color.Red else Gray600,
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
                                    if (index < pinCode.length) Blue else BorderColor
                                )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            CustomKeyboard(
                onNumberClick = { digit ->
                    viewModel.addDigit(digit)
                    showError = false
                },
                onClearClick = {
                    viewModel.clearPin()
                    showError = false
                },
                onDeleteClick = {
                    viewModel.deleteLastDigit()
                    showError = false
                },
                isConfirmMode = false
            )
        }
    }

    private fun handleVerificationSuccess() {
        if (isStandaloneVerification) {
            findNavController().popBackStack()
        } else {
            // Handle non-standalone verification success
            // This is where you would process the payment with the TodoModelItem data
            @Suppress("DEPRECATION")
            val todoItem = arguments?.getSerializable("todoModelItem") as? TodoModelItem
            // Process payment with todoItem data
            findNavController().popBackStack()
        }
    }

    companion object {
        fun newInstance(
            isStandalone: Boolean = true,
            todoModelItem: TodoModelItem? = null
        ): PinVerifyFragment {
            val fragment = PinVerifyFragment()
            val args = Bundle()
            args.putBoolean("isStandalone", isStandalone)
            if (todoModelItem != null) {
                args.putSerializable("todoModelItem", todoModelItem)
            }
            fragment.arguments = args
            return fragment
        }
    }
}


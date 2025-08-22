package com.nexgen.flexiBank.module.view.keypass

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nexgen.flexiBank.component.CustomKeyboard
import com.nexgen.flexiBank.module.view.base.BaseComposeFragment
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Blue
import com.nexgen.flexiBank.utils.theme.BorderColor
import com.nexgen.flexiBank.utils.theme.Gray600
import com.nexgen.flexiBank.utils.theme.White
import com.nexgen.flexiBank.viewmodel.PasscodeViewModel
import com.nexgen.flexiBank.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

class VerifyPinFragment : BaseComposeFragment<PasscodeViewModel, AppRepository>() {
    // Callback to be invoked when PIN verification is successful
    private var onVerificationSuccess: (() -> Unit)? = null

    // Flag to indicate if this is a standalone verification or part of another flow
    private var isStandaloneVerification = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setConfirmMode(false)

        // Check if we have a success callback
        onVerificationSuccess = arguments?.getSerializable("onSuccess") as? () -> Unit

        // Check if this is part of another flow
        isStandaloneVerification = arguments?.getBoolean("isStandalone", true) ?: true

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

    override fun getViewModel(): Class<PasscodeViewModel> = PasscodeViewModel::class.java

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))

    @Composable
    fun VerifyPinScreen(
        viewModel: PasscodeViewModel,
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
            ) {
                Text(
                    text = "Pin Verify",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Enter your pin to login",
                    fontSize = 16.sp,
                    color = if (showError) Color.Red else Gray600,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                )
            }
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
                isConfirmMode = false,
            )
        }
    }

    private fun handleVerificationSuccess() {
        // If we have a success callback, invoke it
        onVerificationSuccess?.invoke()

        // If this is a standalone verification, navigate back
        if (isStandaloneVerification) {
            findNavController().popBackStack()
        }
    }

    private fun handleBiometricAuth() {
        // Handle biometric authentication
        // In a real implementation, this would trigger the biometric prompt
    }

    private fun handleForgotPin() {
        // Handle forgot PIN flow
        // In a real implementation, this would navigate to the forgot PIN screen
    }

    companion object {
        fun newInstance(
            onSuccess: (() -> Unit)? = null,
            isStandalone: Boolean = true
        ): VerifyPinFragment {
            val fragment = VerifyPinFragment()
            val args = Bundle()
            // Note: In a real implementation, you would need to properly serialize the callback
            args.putBoolean("isStandalone", isStandalone)
            fragment.arguments = args
            return fragment
        }
    }
}

@Preview()
@Composable
fun VerifyPinScreenPreview() {
    val viewModel: PasscodeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(AppRepository(object : ApiInterface {})) // Mock repository
    )
    VerifyPinFragment().VerifyPinScreen(viewModel = viewModel)
}
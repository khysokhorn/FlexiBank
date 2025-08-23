package com.nexgen.flexiBank.module.view.keypass

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.component.CustomKeyboard
import com.nexgen.flexiBank.module.view.base.BaseComposeFragment
import com.nexgen.flexiBank.navigation.KhQRCodeNavigationActivity
import com.nexgen.flexiBank.navigation.Screen
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

class ConfirmPasscodeFragment : BaseComposeFragment<PasscodeViewModel, AppRepository>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pin = arguments?.getString("pin") ?: ""
        viewModel.storePin(pin)
        viewModel.setConfirmMode(true)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isNavigateToNext.collect { shouldNavigate ->
                if (shouldNavigate) {
                    navigateToUserVerify()
                    viewModel.resetNavigation()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.apiResponseCode.collect { responseCode ->
                if (responseCode == -1) {
                    val verifyPinFragment = VerifyPinFragment.newInstance(
                        isStandalone = false,
//                        isFromConfirmation = true,
//                        onVerificationSuccess = {
//                            viewModel.submitTransferOrder()
//                        }
                    )
                    val containerId = view.id
                    parentFragmentManager.beginTransaction()
                        .replace(containerId, verifyPinFragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }

        // Listen for transfer order submission result
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.transferOrderSubmitted.collect { isSubmitted ->
                if (isSubmitted) {
                    // Navigate to payment success
                    activity?.let { activity ->
                        activity.startActivity(
                            Intent(
                                activity,
                                KhQRCodeNavigationActivity::class.java
                            ).apply {
                                putExtra("start_destination", Screen.PaymentSuccess.route)
                            })
                        activity.finish()
                    }
                }
            }
        }
    }

    @Composable
    override fun ComposeContent() {
        PassCodeScreen(viewModel = viewModel)
    }

    override fun getViewModel(): Class<PasscodeViewModel> = PasscodeViewModel::class.java;

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))

    private fun navigateToUserVerify() {
        findNavController().navigate(R.id.action_confirmPasscodeFragment_to_userInfoVerifyFragment)
    }

    @Composable
    fun PassCodeScreen(
        viewModel: PasscodeViewModel,
        modifier: Modifier = Modifier
    ) {
        val confirmPinCode by viewModel.confirmPinCode.collectAsState(initial = "")
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
                    text = "Confirm Passcode",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = if (pinMatchError) "PINs don't match. Try again." else "Re-enter your passcode to confirm",
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
                                    if (index < confirmPinCode.length) Blue else BorderColor
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Forgot your PIN?",
                    fontSize = 14.sp,
                    color = Blue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            findNavController().popBackStack()
                            viewModel.resetToCreatePin()
                        }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            CustomKeyboard(
                onNumberClick = { digit -> viewModel.addDigit(digit) },
                onClearClick = { viewModel.clearPin() },
                onDeleteClick = { viewModel.deleteLastDigit() },
                isConfirmMode = true,
            )
        }
    }
}

@Preview
@Composable
fun PassCodeScreenPreview() {
    val viewModel: PasscodeViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel(
            factory = ViewModelFactory(
                AppRepository(object : ApiInterface {})
            )
        )
    ConfirmPasscodeFragment().PassCodeScreen(viewModel = viewModel)
}

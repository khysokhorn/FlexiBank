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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nexgen.flexiBank.R
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

class CreatePasscodeFragment : BaseComposeFragment<PasscodeViewModel, AppRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setConfirmMode(false)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isConfirmPin.collect { isConfirmPin ->
                if (isConfirmPin) {
                    val bundle = bundleOf("pin" to viewModel.getStoredPin())
                    findNavController().navigate(
                        R.id.action_createPasscode_to_confirmPasscode
                        , bundle
                    )
                }
            }
        }
    }

    @Composable
    override fun ComposeContent() {
        PassCodeScreen(viewModel = viewModel)
    }

    override fun getViewModel(): Class<PasscodeViewModel> = PasscodeViewModel::class.java

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))

    @Composable
    fun PassCodeScreen(
        viewModel: PasscodeViewModel,
        modifier: Modifier = Modifier
    ) {
        val pinCode by viewModel.pinCode.collectAsState(initial = "")
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
                    text = "Create Passcode",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Create a new passcode to secure your account",
                    fontSize = 16.sp,
                    color = Gray600,
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
                onNumberClick = { digit -> viewModel.addDigit(digit) },
                onClearClick = { viewModel.clearPin() },
                onDeleteClick = { viewModel.deleteLastDigit() },
                isConfirmMode = false
            )
        }
    }
}

@Preview()
@Composable
fun ComposeContentPreview() {
    val viewModel: PasscodeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(AppRepository(object : ApiInterface {})) // Mock repository
    )
    CreatePasscodeFragment().PassCodeScreen(viewModel = viewModel)
}
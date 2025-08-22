package com.nexgen.flexiBank.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nexgen.flexiBank.module.view.bakongQRCode.KhQRInputAmountScreen
import com.nexgen.flexiBank.module.view.bakongQRCode.viewModel.KhQrInputAmountViewModel

sealed class Screen(val route: String) {
    object KhQRInputAmount : Screen("khqr_input_amount")
    object PaymentSuccess : Screen("payment_success")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.KhQRInputAmount.route
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(Screen.KhQRInputAmount.route) {
                val viewModel: KhQrInputAmountViewModel = hiltViewModel()
                KhQRInputAmountRoute(
                    viewModel = viewModel,
                    onNavigateBack = { navController.navigateUp() },
                    onPaymentSuccess = { navController.navigateToPaymentSuccess() }
                )
            }

            composable(Screen.PaymentSuccess.route) {
                PaymentSuccessRoute(
                    onFinish = { navController.navigateUp() }
                )
            }
        }
    }
}

@Composable
private fun KhQRInputAmountRoute(
    viewModel: KhQrInputAmountViewModel,
    onNavigateBack: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    KhQRInputAmountScreen(
        viewModel = viewModel,
        onNavigateBack = onNavigateBack,
        onPaymentSuccess = onPaymentSuccess
    )
}

@Composable
private fun PaymentSuccessRoute(
    onFinish: () -> Unit
) {
    PaymentSuccessScreen(
        onFinish = onFinish
    )
}

// Navigation extensions
fun NavHostController.navigateToPaymentSuccess() {
    navigate(Screen.PaymentSuccess.route) {
        // Pop up to the start destination to prevent stacking
        popUpTo(Screen.KhQRInputAmount.route) {
            inclusive = true
        }
    }
}

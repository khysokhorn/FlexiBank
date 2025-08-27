package com.nexgen.flexiBank.navigation

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nexgen.flexiBank.module.view.bakongQRCode.componnet.PaymentSuccessScreen
import com.nexgen.flexiBank.module.view.bakongQRCode.model.TodoModelItem
import com.nexgen.flexiBank.module.view.bakongQRCode.screen.KhQRInputAmountScreen
import com.nexgen.flexiBank.module.view.bakongQRCode.viewModel.KhQrInputAmountViewModel
import com.nexgen.flexiBank.module.view.pin.PinVerifyFragmentScreen
import com.nexgen.flexiBank.module.view.pin.viewModel.PinVerifyViewModel

sealed class Screen(val route: String) {
    object KhQRInputAmount : Screen("khqr_input_amount")
    object PaymentSuccess : Screen("payment_success")
    object PinVerifyFragment : Screen("pin_verify_fragment")

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.KhQRInputAmount.route
) {
    val context = LocalContext.current
    val activity = context as? Activity
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
                    onNavigateBack = {
                        activity?.finish()
                        navController.popBackStack()
                    },
                    onPaymentSuccess = { navController.navigateToPaymentSuccess() },
                    navController = navController
                )
            }

            composable(Screen.PaymentSuccess.route) {
                PaymentSuccessRoute()
            }
            composable(
                route = Screen.PinVerifyFragment.route
            ) { backStackEntry ->
                val todoModelItem =
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                        backStackEntry.arguments?.getSerializable(
                            "todoModelItem",
                            TodoModelItem::class.java
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        backStackEntry.arguments?.getSerializable("todoModelItem") as? TodoModelItem
                    }
                PinVerifyFragmentRoute(todoModelItem = todoModelItem)
            }
        }
    }
}

@Composable
private fun KhQRInputAmountRoute(
    viewModel: KhQrInputAmountViewModel,
    onNavigateBack: () -> Unit,
    onPaymentSuccess: () -> Unit,
    navController: NavHostController
) {
    KhQRInputAmountScreen(
        viewModel = viewModel,
        onNavigateBack = onNavigateBack,
        onPaymentSuccess = onPaymentSuccess,
        navController = navController
    )
}

@Composable
private fun PaymentSuccessRoute() {
    PaymentSuccessScreen()
}

@Composable
private fun PinVerifyFragmentRoute(todoModelItem: TodoModelItem?) {
    val viewModel: PinVerifyViewModel = hiltViewModel()
    PinVerifyFragmentScreen(viewModel = viewModel, todoModelItem = todoModelItem)
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

fun NavHostController.navigateToPinVerification(todoModelItem: TodoModelItem? = null) {
    if (todoModelItem != null) {
        navigate(Screen.PinVerifyFragment.route) {
            popUpTo(Screen.PinVerifyFragment.route)
            launchSingleTop = true
        }
        currentBackStackEntry?.arguments?.putSerializable("todoModelItem", todoModelItem)
    } else {
        navigate(Screen.PinVerifyFragment.route) {
            popUpTo(Screen.PinVerifyFragment.route)
        }
    }
}


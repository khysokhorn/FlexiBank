package com.nexgen.flexiBank.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nexgen.flexiBank.utils.theme.FlexiBankTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeNavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startDestination = intent.getStringExtra("start_destination")
            ?: Screen.KhQRInputAmount.route
        setContent {
            FlexiBankTheme {
                AppNavigation(startDestination = startDestination)
            }
        }
    }
}

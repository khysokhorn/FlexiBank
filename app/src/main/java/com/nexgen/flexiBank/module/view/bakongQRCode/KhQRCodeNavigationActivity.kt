package com.nexgen.flexiBank.module.view.bakongQRCode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nexgen.flexiBank.navigation.AppNavigation
import com.nexgen.flexiBank.navigation.Screen
import com.nexgen.flexiBank.utils.theme.FlexiBankTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KhQRCodeNavigationActivity : ComponentActivity() {
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
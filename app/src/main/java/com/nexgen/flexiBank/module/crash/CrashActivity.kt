package com.nexgen.flexiBank.module.crash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nexgen.flexiBank.module.view.MainActivity
import com.nexgen.flexiBank.utils.theme.FlexiBankTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class CrashActivity : ComponentActivity() {
    @Inject
    lateinit var reportSender: CrashReportSender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val errorMessage = intent?.extras?.getString(ERROR_MESSAGE)
        val softwareInfo = intent?.extras?.getString(SOFTWARE_INFO)


        setContent {
            FlexiBankTheme {
                CrashMobileScreen(
                    softwareInfo = softwareInfo,
                    errorMessage = errorMessage,
                    onDismiss = {
                        reportSender.send(
                            errorLog = "$softwareInfo\n=======\n$errorMessage"
                        )

                        finishAffinity()
                        startActivity(
                            Intent(this, MainActivity::class.java)
                        )
                    }
                )
            }
        }
    }
}
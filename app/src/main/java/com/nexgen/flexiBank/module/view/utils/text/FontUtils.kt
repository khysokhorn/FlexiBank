package com.nexgen.flexiBank.module.view.utils.text

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.nexgen.flexiBank.R

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val interFont = GoogleFont("Inter")

val InterNormal = FontFamily(
    Font(googleFont = interFont, fontProvider = provider)
)

val InterSemiBold = FontFamily(Font(
    googleFont = interFont,
    fontProvider = provider,
    weight = FontWeight.SemiBold
))
package com.nexgen.flexiBank.module.view.bakongQRCode.model

import com.nexgen.flexiBank.R

data class Account(
    val id: String,
    val name: String,
    val number: String,
    val balance: String,
    val isDefault: Boolean = false,
    val hasVisa: Boolean = false,
    val iconRes: Int = R.drawable.ic_dollar // Default icon
)
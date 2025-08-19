package com.nexgen.flexiBank.module.view.bakongQRCode.model

data class AccountModel(
    val accountName: String,
    val accountNumber: String, 
    val currency: String,
    val balance: Double,
    val isDefault: Boolean = false,
    val accountType: String = "savings",
    val bankName: String = "Philip Bank",
    val hasVisa: Boolean = false,
)

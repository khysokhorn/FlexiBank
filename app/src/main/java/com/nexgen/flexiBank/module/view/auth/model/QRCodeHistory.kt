package com.nexgen.flexiBank.module.view.auth.model

data class QRCodeHistoryModel(
    var date: String, var list: List<AccountModel>,
)

data class AccountModel(
    var accountName: String, var currencyCode: String, var accountNumber: String, var avatar: String
)

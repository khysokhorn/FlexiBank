package com.nexgen.flexiBank.module.view.bakongQRCode.model

import java.io.Serializable

data class TodoModelItem(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
): Serializable
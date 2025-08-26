package com.nexgen.flexiBank.model

data class BaseModel<T>(
    val data: T,
    val responseCode: Int,
)
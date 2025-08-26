package com.nexgen.flexiBank.repository

import com.nexgen.flexiBank.model.BaseModel
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.network.Resource

class AppRepository(
    private val api: ApiInterface,
) : BaseRepository() {
    suspend fun submitKhQrPayment(
        amount: Double,
        accountId: String,
        remark: String
    ): Resource<BaseModel<String>> = safeApiCall { 
        // This ensures the request actually goes through the network stack
        // and triggers the PinVerificationInterceptor
        api.getHandshake()
    }
}


package com.nexgen.flexiBank.repository

import com.nexgen.flexiBank.network.ApiInterface

class AppRepository(
    private val api: ApiInterface,
) : BaseRepository() {
    suspend fun submitKhQrPayment(amount: Double, accountId: String, remark: String): Result<Unit> {
        return try {
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

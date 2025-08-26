package com.nexgen.flexiBank.network

import com.nexgen.flexiBank.model.BaseModel
import retrofit2.http.GET

interface ApiInterface {
    @GET("/todos")
    suspend fun getHandshake(): BaseModel<String>
}

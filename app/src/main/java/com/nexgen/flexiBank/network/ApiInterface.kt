package com.nexgen.flexiBank.network

import com.nexgen.flexiBank.model.BaseModel
import com.nexgen.flexiBank.module.view.bakongQRCode.model.TodoModelItem
import retrofit2.http.GET

interface ApiInterface {
    @GET("/todos/1")
    suspend fun getHandshake(): BaseModel<TodoModelItem>
}

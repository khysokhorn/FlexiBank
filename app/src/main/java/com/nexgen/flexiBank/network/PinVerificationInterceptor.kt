package com.nexgen.flexiBank.network

import android.content.Context
import android.widget.Toast
import com.nexgen.flexiBank.model.BaseModel
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class PinVerificationInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        logRequest(originalRequest)

        val response = chain.proceed(originalRequest)

        return handleResponse(response, context)
    }

    private fun logRequest(request: Request) {
        val requestBody = request.body
    }

    private fun handleResponse(response: Response, context: Context): Response {
        try {
            if (response.code == 401) {
                throw IOException("You are not authorized to enter system.")
            }

            val responseBody = response.body
            val contentType = responseBody.contentType()
            val responseBytes = responseBody.bytes()
            val responseString = String(responseBytes)

            val baseModel = BaseModel(data = responseString, responseCode = -1)

            if (baseModel.responseCode == -1) {
                Toast.makeText(context, "Show Verify pin", Toast.LENGTH_LONG).show()
            }
            return response.newBuilder()
                .body(responseBytes.toResponseBody(contentType))
                .build()

        } catch (e: Exception) {
            if (e is IOException) throw e
            throw IOException("Error processing response: ${e.message}", e)
        }
    }

}

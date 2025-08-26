package com.nexgen.flexiBank.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class PinVerificationInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        logRequest(originalRequest)

        val response = chain.proceed(originalRequest)

        return handleResponse(originalRequest, response)
    }

    private fun logRequest(request: Request) {
        val requestBody = request.body
    }

    private fun handleResponse(
        originalRequest: Request,
        response: Response
    ): Response {
        val responseBody = response.body
        val contentType = responseBody.contentType()
        val responseBytes = responseBody.bytes()
        return response.newBuilder()
            .body(responseBytes.toResponseBody(contentType))
            .build()
    }

}

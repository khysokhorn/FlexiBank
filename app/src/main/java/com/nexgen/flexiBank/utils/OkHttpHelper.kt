package com.nexgen.flexiBank.utils

import com.nexgen.flexiBank.module.crash.HttpMethod
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

/**
 * The default User-Agent header value.
 * */
const val USER_AGENT =
    "Mozilla/5.0 (Windows NT 10.0; WOW64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36 OPR/113.0.0.0"

private const val REQUIRES_BODY_ERROR_MESSAGE =
    "The method request doesn't require a body. Are you using a function that requires a body?"

/**
 * Creates a form request using HTTP POST method.
 * @param url The URL for the request.
 * @param method The HTTP method for the request.
 * @param body The form data to be sent with the request.
 * @param headers The headers for the request.
 * @param userAgent The User-Agent header for the request.
 * @return A [Call] object representing the request.
 */
fun OkHttpClient.formRequest(
    url: String,
    method: HttpMethod,
    body: Map<String, String>,
    headers: Headers = Headers.headersOf(),
    userAgent: String? = null,
): Call {
    require(method.requiresBody) {
        REQUIRES_BODY_ERROR_MESSAGE
    }

    return request(
        url = url,
        method = method,
        body = body.toFormBody(),
        headers = headers,
        userAgent = userAgent
    )
}

/**
 * Creates a JSON request using the specified HTTP method.
 * @param url The URL for the request.
 * @param method The HTTP method for the request.
 * @param json The JSON data to be sent with the request. It could be a json string.
 * @param headers The headers for the request.
 * @param userAgent The User-Agent header for the request.
 * @return A [Call] object representing the request.
 */
fun OkHttpClient.jsonRequest(
    url: String,
    method: HttpMethod,
    json: Any,
    headers: Headers = Headers.headersOf(),
    userAgent: String? = null,
): Call {
    require(method.requiresBody) {
        REQUIRES_BODY_ERROR_MESSAGE
    }

    return request(
        url = url,
        method = method,
        body = jsonToRequestBody(json),
        headers = headers,
        userAgent = userAgent
    )
}

/**
 * Creates a request with a generic body using the specified HTTP method.
 * @param url The URL for the request.
 * @param method The HTTP method for the request.
 * @param body The body data to be sent with the request.
 * @param mediaType The [MediaType] of the request body.
 * @param headers The headers for the request.
 * @param userAgent The User-Agent header for the request.
 * @return A [Call] object representing the request.
 */
fun OkHttpClient.genericBodyRequest(
    url: String,
    method: HttpMethod,
    body: String,
    mediaType: MediaType?,
    headers: Headers = Headers.headersOf(),
    userAgent: String? = null,
): Call {
    require(method.requiresBody) {
        REQUIRES_BODY_ERROR_MESSAGE
    }

    return request(
        url = url,
        method = method,
        body = body.toRequestBody(mediaType),
        headers = headers,
        userAgent = userAgent
    )
}

/**
 * Creates a generic HTTP request.
 * @param url The URL for the request.
 * @param method The HTTP method for the request.
 * @param body The body data to be sent with the request.
 * @param headers The headers for the request.
 * @param userAgent The User-Agent header for the request.
 * @return A [Call] object representing the request.
 */
fun OkHttpClient.request(
    url: String,
    method: HttpMethod = HttpMethod.GET,
    body: RequestBody? = null,
    headers: Headers = Headers.headersOf(),
    userAgent: String? = null,
): Call {
    val headersToUse = Headers.Builder()
        .addAll(headers)
        .apply {
            if (userAgent != null)
                add("User-Agent", userAgent)
        }
        .build()

    var request = Request.Builder()
        .url(url)
        .headers(headersToUse)

    if (method.requiresBody) {
        request = request
            .method(method.name, body)
    } else if (body != null) {
        throw IllegalArgumentException("The request has a body but the method doesn't require one. Consider using the correct method request.")
    }

    return newCall(request.build())
}

/**
 * Converts a map of key-value pairs to a [FormBody] object.
 * @receiver The map to be converted.
 * @return The converted [FormBody].
 */
private fun Map<String, String>.toFormBody(): FormBody {
    val builder = FormBody.Builder()
    forEach {
        builder.addEncoded(it.key, it.value)
    }
    return builder.build()
}

/**
 * Converts JSON data to a [RequestBody] object.
 * @param data The JSON data to be converted.
 * @return The converted [RequestBody].
 * @throws IllegalArgumentException if the provided request data is not a valid JSON object.
 */
private fun jsonToRequestBody(data: Any): RequestBody {
    val jsonString = when {
        data is JSONObject -> data.toString()
        data is JSONArray -> data.toString()
        data is String && isJson(data) -> data
        else -> throw IllegalArgumentException("The provided request data is not a valid JSON object.")
    }

    val jsonType = "application/json;charset=utf-8".toMediaTypeOrNull()

    return jsonString.toRequestBody(jsonType)
}

/**
 * Checks if a string represents a valid JSON object.
 * @param test The string to be checked.
 * @return `true` if the string represents a valid JSON object, `false` otherwise.
 */
private fun isJson(test: String): Boolean {
    return safeCall {
        JSONObject(test)
        true
    } ?: safeCall {
        JSONArray(test)
        true
    } ?: false
}

/**
 * Creates an OkHttp [Call] for a request with the specified [URL].
 *
 * This is a convenience function that delegates to [request] with a String URL.
 *
 * @param url The [URL] of the request.
 * @param method The HTTP method (default: GET).
 * @param body The request body (optional).
 * @param headers The request headers (optional).
 * @param userAgent The user agent for the request (optional).
 * @return An OkHttp [Call] object representing the request.
 */
fun OkHttpClient.request(
    url: URL,
    method: HttpMethod = HttpMethod.GET,
    body: RequestBody? = null,
    headers: Headers = Headers.headersOf(),
    userAgent: String? = null,
): Call = request(
    url = url.toString(),
    method = method,
    body = body,
    headers = headers,
    userAgent = userAgent
)

/**
 * Creates an OkHttp [Call] for a request with the specified [HttpUrl].
 *
 * This is a convenience function that delegates to [request] with a String URL.
 *
 * @param url The [HttpUrl] of the request.
 * @param method The HTTP method (default: GET).
 * @param body The request body (optional).
 * @param headers The request headers (optional).
 * @param userAgent The user agent for the request (optional).
 * @return An OkHttp [Call] object representing the request.
 */
fun OkHttpClient.request(
    url: HttpUrl,
    method: HttpMethod = HttpMethod.GET,
    body: RequestBody? = null,
    headers: Headers = Headers.headersOf(),
    userAgent: String? = null,
): Call = request(
    url = url.toString(),
    method = method,
    body = body,
    headers = headers,
    userAgent = userAgent
)

/**
 * Executes the provided lambda [unsafeCall] safely, catching any exceptions and logging them.
 *
 * @param unsafeCall The lambda representing the possibly unsafe call.
 * @param message The optional message to log when the block fails.
 * @return The result of the lambda if it executes successfully, otherwise null.
 */
inline fun <T> safeCall(message: String? = null, unsafeCall: () -> T?): T? {
    return try {
        unsafeCall()
    } catch (e: Throwable) {
        errorLog(message ?: e.stackTraceToString())
        null
    }
}

/**
 * Returns the most relevant error message from a [Throwable].
 *
 * This property prioritizes the localized message (`localizedMessage`), then falls back to the general message (`message`),
 * and finally defaults to "UNKNOWN ERR" if neither is available.
 */
val Throwable.actualMessage: String
    get() = localizedMessage ?: message ?: "UNKNOWN ERR"
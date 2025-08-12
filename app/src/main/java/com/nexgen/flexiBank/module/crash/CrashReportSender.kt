package com.nexgen.flexiBank.module.crash

import android.content.Context
import com.nexgen.flexiBank.utils.AppDispatchers
import com.nexgen.flexiBank.utils.errorLog
import com.nexgen.flexiBank.utils.formRequest
import com.nexgen.flexiBank.utils.showToast
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import javax.inject.Inject
import com.nexgen.flexiBank.R as LocaleR

internal const val errorReportFormUrl =
    "https://docs.google.com/forms/u/0/d/e/1FAIpQLSfTVmgiOeF7RlDbjBR10RQG6C6uKioSk-toqKecPvpkAe9ffw/formResponse?pli=1"

enum class HttpMethod(val requiresBody: Boolean = false) {
    /**
     * The GET method requests a representation of the specified resource.
     * Requests using GET should only retrieve data and should have no other effect.
     */
    GET,

    /**
     * The POST method submits an entity to the specified resource,
     * often causing a change in state or side effects on the server.
     */
    POST(requiresBody = true),

    /**
     * The PUT method replaces all current representations of the target
     * resource with the request payload.
     */
    PUT(requiresBody = true),

    /**
     * The DELETE method deletes the specified resource.
     */
    DELETE,

    /**
     * The PATCH method partially modifies the specified resource.
     */
    PATCH(requiresBody = true),

    /**
     * The HEAD method asks for a response identical to a GET request,
     * but without the response body. Useful for retrieving meta-information.
     */
    HEAD,

    /**
     * The OPTIONS method describes the communication options for the target resource.
     */
    OPTIONS,

    /**
     * The TRACE method performs a message loop-back test along the path to the target resource.
     */
    TRACE;

    /**
     * Indicates whether this HTTP method is considered safe.
     * Safe methods should not change the state of the server.
     *
     * @return true if the method is safe, false otherwise
     */
    val isSafe: Boolean
        get() = this in setOf(GET, HEAD, OPTIONS, TRACE)

    /**
     * Indicates whether this HTTP method is idempotent.
     * Idempotent methods can be called multiple times without different outcomes.
     *
     * @return true if the method is idempotent, false otherwise
     */
    val isIdempotent: Boolean
        get() = this in setOf(GET, PUT, DELETE, HEAD, OPTIONS, TRACE)

    /**
     * The companion object provides utility methods for working with HttpMethod enum values.
     * */
    companion object {
        /**
         * Parses a string representation of an HTTP method and returns the corresponding HttpMethod enum.
         *
         * @param method The string representation of the HTTP method.
         * @return The corresponding HttpMethod enum value.
         * @throws IllegalArgumentException if the method string doesn't match any known HTTP method.
         */
        @Throws(IllegalArgumentException::class)
        fun parse(method: String): HttpMethod {
            return try {
                HttpMethod.valueOf(method.uppercase())
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Unknown HTTP method: $method", e)
            }
        }
    }
}

interface CrashReportSender {
    /**
     *
     * Sends error logs to server (docs forms).
     *
     * @param errorLog error log/stack trace/message
     *
     * @return true if it was sent successfully, otherwise false.
     * */
    fun send(errorLog: String)
}


internal class DefaultCrashReportSender @Inject constructor(
    private val client: OkHttpClient,
    @ApplicationContext private val context: Context
) : CrashReportSender {
    override fun send(errorLog: String) {
       errorLog("errorLog : $errorLog")
        AppDispatchers.Default.scope.launch {
            val response = client.formRequest(
                url = errorReportFormUrl,
                method = HttpMethod.POST,
                body = mapOf("entry.1687138646" to errorLog)
            ).execute()
            val responseString = response.body.string()

            val isSent = response.isSuccessful && (responseString.contains(
                "form_confirm",
                true
            ) || responseString.contains("submit another response", true))

            if (!isSent) {
                context.run {
                    showToast(getString(LocaleR.string.fail_sending_error_log))
                }
            }
        }
    }
}

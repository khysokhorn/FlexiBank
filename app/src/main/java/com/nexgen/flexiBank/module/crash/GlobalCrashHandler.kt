package com.nexgen.flexiBank.module.crash

import android.content.Context
import android.content.Intent
import android.os.Build
import com.nexgen.flexiBank.utils.errorLog
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

const val ERROR_MESSAGE = "error_message"
const val SOFTWARE_INFO = "software_info"

fun Throwable.fullMessage(): String {
    val sw = StringWriter()
    this.printStackTrace(PrintWriter(sw))
    return sw.toString()
}

class GlobalCrashHandler(
    private val context: Context
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, exception: Throwable) {
        try {
            val softwareInfo = getSoftwareInfo()
            val errorBy = exception.cause?.message
            val errorMessage = "${errorBy}\n\n${exception.stackTraceToString()}"
            errorLog(exception)
            errorLog(softwareInfo)

            val intent = Intent(context, CrashActivity::class.java).apply {
                putExtra(ERROR_MESSAGE, errorMessage)
                putExtra(SOFTWARE_INFO, softwareInfo)

                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }

            context.startActivity(intent)
            exitProcess(0)
        } catch (_: Exception) {
            val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
            defaultHandler?.uncaughtException(thread, exception)
        }
    }

    private fun getSoftwareInfo(): String {
        val softwareInfo = StringBuilder()
        softwareInfo.append("Device: ")
        softwareInfo.append(Build.BRAND)
        softwareInfo.append(" ")
        softwareInfo.append(Build.MODEL)
        softwareInfo.append("\nSDK: ")
        softwareInfo.append(Build.VERSION.SDK_INT)
        softwareInfo.append("\nApp version: ")

        return softwareInfo.toString()
    }

    companion object {
        fun initialize(
            applicationContext: Context,
        ) {
            val handler = GlobalCrashHandler(applicationContext)
            Thread.setDefaultUncaughtExceptionHandler(handler)
        }
    }
}
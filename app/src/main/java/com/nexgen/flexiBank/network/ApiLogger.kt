package com.nexgen.flexiBank.network

import okhttp3.logging.HttpLoggingInterceptor


class ApiLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        "Logger"
//        if (message.startsWith("{") || message.startsWith("[")) {
//            try {
//                val prettyPrintJson = GsonBuilder().setPrettyPrinting()
//                    .create().toJson(JsonParser().parse(message))
//                Log.d(logName, prettyPrintJson)
//            } catch (m: JsonSyntaxException) {
//                Log.d(logName, message)
//            }
//        } else {
//            Log.d(logName, message)
//            return
//        }
    }
}

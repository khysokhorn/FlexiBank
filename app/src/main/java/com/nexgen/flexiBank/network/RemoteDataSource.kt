package com.nexgen.flexiBank.network


import android.content.Context
import com.nexgen.flexiBank.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteDataSource() {
    fun <Api> buildApi(
        context: Context,
        api: Class<Api>
    ): Api {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .addInterceptor(PinVerificationInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
    fun <Api> buildImageApi(
        api: Class<Api>,
    ): Api {
        val logging = HttpLoggingInterceptor(ApiLogger())
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(2, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(2, TimeUnit.MINUTES) // write timeout
            .readTimeout(2, TimeUnit.MINUTES) // read timeout

        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}

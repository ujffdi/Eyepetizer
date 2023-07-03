package com.tongsr.common.webview.utils

import com.tongsr.common.webview.service.WebApiService
import com.tongsr.data.remote.logging.Level
import com.tongsr.data.remote.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HttpUtils {

    val apiService: WebApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.baidu.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient().build())
            .build()
            .create(WebApiService::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            addInterceptor(LoggingInterceptor.Builder()
                .setLevel(Level.BASIC)
                .build())
        }
    }

}
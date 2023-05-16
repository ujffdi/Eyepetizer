package com.tongsr.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tongsr.data.remote.adapter.NullStringAdapter
import com.tongsr.data.remote.logging.Level
import com.tongsr.data.remote.logging.LoggingInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Tongsr
 * @version 1.0
 * @date 2023/2/22
 * @email ujffdtfivkg@gmail.com
 * @description 网络客户端
 */
object NetworkClient {

    private const val DEFAULT_TIMEOUT_SECONDS = 15L

    private val moshi: Moshi by lazy {
        Moshi.Builder().add(NullStringAdapter).add(KotlinJsonAdapterFactory()).build()
    }

    private val moshiConverterFactory: MoshiConverterFactory by lazy {
        MoshiConverterFactory.create(moshi)
    }

    private val okHttpClientBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
    }

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
    }

    /**
     * 创建 Retrofit 客户端
     *
     * @param baseUrl baseUrl
     * @param timeoutSeconds 默认超时时间
     * @param okHttpClient OkHttpClient
     * @param interceptors 拦截器
     */
    fun create(
        baseUrl: String,
        timeoutSeconds: Long = DEFAULT_TIMEOUT_SECONDS,
        okHttpClient: OkHttpClient? = null,
        interceptors: List<Interceptor>? = null
    ): Retrofit {
        val client =
            okHttpClient ?: okHttpClientBuilder.connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .writeTimeout(timeoutSeconds, TimeUnit.SECONDS).addInterceptor(
                    LoggingInterceptor.Builder().setLevel(Level.BASIC).log(Platform.INFO).build()
                ).apply {
                    interceptors?.forEach {
                        addInterceptor(it)
                    }
                }.build()
        return retrofitBuilder.baseUrl(baseUrl).addConverterFactory(moshiConverterFactory)
            .client(client).build()
    }

    /**
     * WanApp Api 客户端
     */
    val wanApiClient = create(BASE_URL)

}
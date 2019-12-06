package com.butterfly.test.network.clients

import com.butterfly.test.BuildConfig
import com.butterfly.test.network.NetworkModule.mapper
import com.butterfly.test.network.modules.base.NullOnEmptyConverterFactory
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

class ServerClient(endpoint: String) {

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"

        private const val SERVER_TIMEOUT_IN_SECONDS = 30L
    }

    val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create(mapper))
        .baseUrl(endpoint)
        .client(createHttpClient())
        .build()

    private fun log() = LoggingInterceptor.Builder()
        .loggable(BuildConfig.DEBUG)
        .setLevel(Level.BASIC)
        .log(Platform.INFO)
        .request("Request>>>>")
        .response("Response<<<<")
        .build()

    private fun createHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(SERVER_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .readTimeout(SERVER_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(SERVER_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                requestBuilder.method(original.method, original.body)
                return chain.proceed(addHeader(requestBuilder))
            }
        })
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(log())
                addInterceptor(OkHttpProfilerInterceptor())
            }
        }.build()

    private fun addHeader(requestBuilder: Request.Builder): Request = requestBuilder
        .removeHeader(HEADER_AUTHORIZATION)
        .build()
}

package com.example.data.api.interceptor

import com.example.data.BuildConfig
import com.example.security.hash
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.url().newBuilder()
        val timestamp = System.currentTimeMillis().toString()
        val hashKey = timestamp + BuildConfig.MARVEL_API_PRIVATE_KEY + BuildConfig.MARVEL_API_PUBLIC_KEY
        return chain.proceed(
            request.newBuilder().url(
                builder.addQueryParameter("ts", timestamp)
                    .addQueryParameter("apikey", BuildConfig.MARVEL_API_PUBLIC_KEY)
                    .addQueryParameter("hash", hashKey.hash("MD5"))
                    .build()
            ).build()
        )
    }
}
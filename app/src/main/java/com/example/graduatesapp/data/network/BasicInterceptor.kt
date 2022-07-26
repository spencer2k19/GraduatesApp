package com.example.graduatesapp.data.network

import okhttp3.Interceptor
import okhttp3.Response

class BasicInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticateRequest=request.newBuilder()
            .header("Accept","application/json")
            .build()
        return chain.proceed(authenticateRequest)
    }
}
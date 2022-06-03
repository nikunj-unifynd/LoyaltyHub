package com.lib.mylibrary.data.remote

import com.lib.mylibrary.core.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", Constants.AUTH_TOKEN)
            .header("mallid", Constants.MALL_ID).build()
        return chain.proceed(authenticatedRequest)
    }

}
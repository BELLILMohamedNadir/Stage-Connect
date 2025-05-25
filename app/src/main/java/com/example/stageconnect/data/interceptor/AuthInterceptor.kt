package com.example.stageconnect.data.interceptor

import android.content.SharedPreferences
import com.example.stageconnect.domain.CONSTANT
import com.example.stageconnect.domain.CONSTANT.JWT_TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPreferences.getString(JWT_TOKEN, null)
        val requestBuilder = chain.request().newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}

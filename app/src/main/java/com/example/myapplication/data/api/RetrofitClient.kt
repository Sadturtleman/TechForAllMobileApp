package com.example.myapplication.data.api

import android.util.Log
import com.example.myapplication.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://dapi.kakao.com/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val headerValue = "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}"

            Log.d("HTTP_AUTH", "Request Header = $headerValue")

            val request = chain.request().newBuilder()
                .addHeader("Authorization", headerValue)
                .build()

            chain.proceed(request)
        }
        .build()

    val api: KakaoLocalApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(KakaoLocalApi::class.java)
}

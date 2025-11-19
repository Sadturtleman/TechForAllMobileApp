package com.example.myapplication.data.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object KakaoSpeechClient {
    private const val BASE_URL = "https://kakaoi-newtone-openapi.kakao.com/"

    val api: KakaoSpeechApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(KakaoSpeechApi::class.java)
    }
}
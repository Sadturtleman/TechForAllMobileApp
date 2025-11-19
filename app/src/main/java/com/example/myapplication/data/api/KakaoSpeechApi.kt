package com.example.myapplication.data.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface KakaoSpeechApi {

    @Headers(
        "Content-Type: application/octet-stream"
    )
    @POST("v1/recognize")
    suspend fun recognize(
        @Header("Authorization") apiKey: String,
        @Body audio: RequestBody
    ): Response<ResponseBody>
}

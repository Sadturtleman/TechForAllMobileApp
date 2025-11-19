package com.example.myapplication.data.api

import com.example.myapplication.data.request.GeminiRequest
import com.example.myapplication.data.response.GeminiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GeminiApi {

    @POST("v1beta/models/{model}:generateContent")
    suspend fun generateText(
        @Path("model") model: String,
        @Query("key") apiKey: String,
        @Body body: GeminiRequest
    ): GeminiResponse
}
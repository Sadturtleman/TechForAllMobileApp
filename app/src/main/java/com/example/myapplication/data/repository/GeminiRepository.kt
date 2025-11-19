package com.example.myapplication.data.repository

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.api.RetrofitClientGemini
import com.example.myapplication.data.request.Content
import com.example.myapplication.data.request.GeminiRequest
import com.example.myapplication.data.request.Part

class GeminiRepository {

    suspend fun ask(text: String): String {
        val res = RetrofitClientGemini.api.generateText(
            model = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMINI_API_KEY,
            body = GeminiRequest(
                contents = listOf(
                    Content(
                        parts = listOf(Part(text))
                    )
                )
            )
        )

        return res.candidates
            .firstOrNull()
            ?.content?.parts?.firstOrNull()?.text
            ?: "응답 없음"
    }
}

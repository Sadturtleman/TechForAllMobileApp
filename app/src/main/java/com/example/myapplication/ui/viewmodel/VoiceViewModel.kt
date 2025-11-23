package com.example.myapplication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VoiceViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _text = MutableStateFlow("")
    val text = _text.asStateFlow()

    private val _keywords = MutableStateFlow("")
    val keywords = _keywords.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    fun onVoiceInput(input: String) {
        Log.d("VoiceViewModel", "Input: $input")

        viewModelScope.launch {
            try {
                val response = generativeModel.generateContent(
                    """
    문장: "$input"
    출력 규칙:
    - 목적지만 단일 단어로 출력
    - 따옴표 금지
    - 분석 금지
    - 설명 금지
    - 한 글자라도 다른 말 추가 금지
    - 예시: 서울역 → 서울역

    출력:
    """.trimIndent()
                )

                val result = response.text ?: ""

                Log.d("VoiceViewModel", "Gemini Extracted: $result")
                _keywords.value = result

            } catch (e: Exception) {
                Log.e("VoiceViewModel", "Gemini Error: ${e.message}")
                _keywords.value = "Error: ${e.message}"
            }
        }
    }
}

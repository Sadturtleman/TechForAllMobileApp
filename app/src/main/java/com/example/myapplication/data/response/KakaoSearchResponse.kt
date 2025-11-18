package com.example.myapplication.data.response

data class KakaoSearchResponse(
    val documents: List<KakaoDocument>
)

data class KakaoDocument(
    val placeName: String,
    val addressName: String,
    val x: String,   // longitude
    val y: String    // latitude
)

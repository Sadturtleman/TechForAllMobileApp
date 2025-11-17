package com.example.myapplication.data.model

@kotlinx.serialization.Serializable
data class SearchResult(
    val placeName: String,
    val address: String
)

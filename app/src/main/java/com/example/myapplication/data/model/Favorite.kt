package com.example.myapplication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Favorite(
    val title: String,
    val address: String
)
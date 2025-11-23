package com.example.myapplication.data.model

import com.kakao.vectormap.LatLng


data class SearchResult(
    val placeName: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
){
    val latLng: LatLng
        get() = LatLng.from(latitude, longitude)
}
